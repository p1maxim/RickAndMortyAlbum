package com.example.rickandmortyalbum.data.repository

import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.remote.CharacterRemoteService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

// Paging source to get Characters data from Network without caching in local DB
class CharacterPagingSource @Inject constructor(
    private val characterRemoteService: CharacterRemoteService,
    private val nameOfCharacter: String?
) : PagingSource<Int, CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        return try {
            val pageNumber = params.key ?: 1
            val response = characterRemoteService.getAllCharacters(pageNumber, nameOfCharacter)
            val prevPage = if (pageNumber > 0) pageNumber - 1 else null
            val nextPage = if (response.body()?.info?.next != null) {
                response.body()?.info?.next?.toUri()?.getQueryParameter("page")?.toInt()
            } else null
            LoadResult.Page(
                data = response.body()?.results ?: emptyList(),
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (ex: HttpException){
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorCharPage = state.closestPageToPosition(anchorPosition)
            anchorCharPage?.prevKey?.plus(1) ?: anchorCharPage?.nextKey?.minus(1)
        }
    }
}