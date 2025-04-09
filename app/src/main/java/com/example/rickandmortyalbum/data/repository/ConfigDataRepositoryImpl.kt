package com.example.rickandmortyalbum.data.repository

import com.example.rickandmortyalbum.domain.ConfigDataRepository
import javax.inject.Inject

class ConfigDataRepositoryImpl @Inject constructor(
    val dataStoreManager: DataStoreManager
): ConfigDataRepository {

    // Set value for UseDbFlag to Data Store
    override suspend fun setUseDbFlag(useDb: Boolean) {
        dataStoreManager.saveUseDbFlag(useDb)
    }

    // Get value for UseDbFlag to Data Store
    override fun getUseDbFlag() = dataStoreManager.readUseDbFlag()
}