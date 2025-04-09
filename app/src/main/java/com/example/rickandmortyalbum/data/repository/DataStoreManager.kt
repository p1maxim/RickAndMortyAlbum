package com.example.rickandmortyalbum.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveUseDbFlag(useDb: Boolean)
    fun readUseDbFlag(): Flow<Boolean>
}