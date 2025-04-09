package com.example.rickandmortyalbum.domain

import kotlinx.coroutines.flow.Flow

interface ConfigDataRepository {
    suspend fun setUseDbFlag(useDb: Boolean)
    fun getUseDbFlag(): Flow<Boolean>
}