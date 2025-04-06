package com.example.rickandmortyalbum.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.Exception

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManagerImpl @Inject constructor(
    private val context: Context
): DataStoreManager {

    // Save value to datastore
    override suspend fun saveUseDbFlag(useDb: Boolean) {
        try {
            context.dataStore.edit { prefs ->
                prefs[USE_DB_KEY] = useDb
            }
        } catch (e: Exception) {
            Timber.e("Exception due to store useDbFlag to DataStore: ${e.message}")
        }

    }

    // Get value to datastore
    override fun readUseDbFlag(): Flow<Boolean> {
        return try {
            context.dataStore.data
                .map { prefs ->
                    prefs[USE_DB_KEY] == true
                }
        } catch (e: IOException) {
            Timber.e("Exception due to read useDbFlag from DataStore: ${e.message}")
            flowOf(false)
        }
    }

    // Key for stored parameter
    companion object {
        val USE_DB_KEY = booleanPreferencesKey("use_db")
    }
}