package com.example.rickandmortyalbum.data.repository

import androidx.paging.PagingData
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.model.DataResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(name: String?) : Flow<PagingData<CharacterItem>>

    fun getCharacter(id: Int) : Flow<DataResponse<CharacterItem>>
}