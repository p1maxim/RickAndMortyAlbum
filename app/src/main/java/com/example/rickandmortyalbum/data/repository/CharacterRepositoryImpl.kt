package com.example.rickandmortyalbum.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyalbum.data.db.AlbumDb
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.remote.CharacterRemoteService
import com.example.rickandmortyalbum.data.model.DataResponse
import com.example.rickandmortyalbum.domain.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val localDb: AlbumDb,
    private val apiService: CharacterRemoteService,
    private val dataStoreManager: DataStoreManager
) : CharacterRepository {

    val useDbFlow = dataStoreManager.readUseDbFlag()
    val pagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        initialLoadSize = 100
    )

    // Get list of characters
    // The real source of fetched data (local or remote) will be reported to log
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters(name: String?): Flow<PagingData<CharacterItem>> {
        return if(useDbFlow.first()) {
            Timber.d("Character data will be fetched from cache of DB")
            Pager(
                config = pagingConfig,
                remoteMediator = CharacterRemoteMediator(
                    apiService = apiService,
                    db = localDb,
                    nameOfCharacter = if (name.isNullOrBlank()) null else name
                ),
                pagingSourceFactory = { localDb.characterDao().pagingSource() }
            ).flow
        } else {
            Timber.d("Character data fetched from Network without DB")
            Pager(
                config = pagingConfig,
                pagingSourceFactory = {
                    CharacterPagingSource(apiService, if (name.isNullOrBlank()) null else name)
                }
            ).flow
        }
    }

    // Get one character by Id
    // The real source of fetched data (local or remote) will be reported to log
    override fun getCharacter(id: Int): Flow<DataResponse<CharacterItem>> = channelFlow {
        if(useDbFlow.first()) {
            localDb.characterDao().getCharacter(id).collect { cachedItem ->
                if (cachedItem != null) {
                    Timber.d("Character data was fetched from DB")
                    send(DataResponse.success(cachedItem))
                } else {
                    try {
                        send(DataResponse.loading())
                        val networkItem = apiService.getCharacter(id).body()
                        if (networkItem != null) {
                            Timber.d("Character data was fetched from Network and stored to DB")
                            localDb.characterDao().insert(networkItem)
                            send(DataResponse.success(networkItem))
                        } else {
                            send(DataResponse.error("This character not found on Server"))
                        }
                    } catch (e: Exception) {
                        send(DataResponse.error(e.message ?: "Unknown error"))
                    }
                }
            }
        } else {
            Timber.d("Character data fetched from Network without DB")
            apiService.getCharacter(id).body()?.let {
                send(DataResponse.success(it))
            } ?: send(DataResponse.error("This character not found on Server"))
        }
    }
}