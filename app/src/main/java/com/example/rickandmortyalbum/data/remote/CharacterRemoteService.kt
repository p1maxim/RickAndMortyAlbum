package com.example.rickandmortyalbum.data.remote

import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.model.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterRemoteService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterItem>
}