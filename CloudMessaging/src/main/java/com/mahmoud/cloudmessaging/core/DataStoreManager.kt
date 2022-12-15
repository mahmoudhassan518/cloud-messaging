package com.mahmoud.cloudmessaging.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun putDataStoreString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit {
            it[preferencesKey] = value
        }
    }

    suspend fun putDataStoreInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit {
            it[preferencesKey] = value
        }
    }

    suspend fun getDataStoreString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val dataPref: Preferences = dataStore.data.first()
        return dataPref[preferencesKey]
    }

    suspend fun getDataStoreInt(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        val dataPref: Preferences = dataStore.data.first()
        return dataPref[preferencesKey]
    }

    suspend fun clearDataStoreCache() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun deleteDataStoreKey(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit {
            it.remove(preferencesKey)
        }
    }
}
