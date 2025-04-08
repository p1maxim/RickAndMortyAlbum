package com.example.rickandmortyalbum.data.remote

import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyalbum.data.model.CharacterItem
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterRemoteService: CharacterRemoteService,
    private val nameOfCharacter: String?
) : PagingSource<Int, CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        return try {
            val pageNumber = params.key ?: 1
            val response = characterRemoteService.getAllCharacters(pageNumber, nameOfCharacter)
            val prevPage = if (pageNumber > 0) pageNumber - 1 else null
            val nextPage = if (response.body()?.page?.next != null) {
                response.body()?.page?.next?.toUri()?.getQueryParameter("page")?.toInt()
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