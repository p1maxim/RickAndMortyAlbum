package com.example.rickandmortyalbum.data.repository

import com.example.rickandmortyalbum.data.remote.DataStoreManager
import javax.inject.Inject

class ConfigDataRepositoryImpl @Inject constructor(
    val dataStoreManager: DataStoreManager
): ConfigDataRepository {

    override suspend fun setUseDbFlag(useDb: Boolean) {
        dataStoreManager.saveUseDbFlag(useDb)
    }

    override fun getUseDbFlag() = dataStoreManager.readUseDbFlag()
}