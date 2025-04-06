package com.example.rickandmortyalbum.data.repository

import kotlinx.coroutines.flow.Flow

interface ConfigDataRepository {
    suspend fun setUseDbFlag(useDb: Boolean)
    fun getUseDbFlag(): Flow<Boolean>
}