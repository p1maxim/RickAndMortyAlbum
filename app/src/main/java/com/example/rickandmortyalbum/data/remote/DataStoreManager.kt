package com.example.rickandmortyalbum.data.remote

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveUseDbFlag(useDb: Boolean)
    fun readUseDbFlag(): Flow<Boolean>
}