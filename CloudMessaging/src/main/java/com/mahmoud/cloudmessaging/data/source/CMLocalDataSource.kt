package com.mahmoud.cloudmessaging.data.source

import com.mahmoud.cloudmessaging.core.DatastoreManager
import javax.inject.Inject

class CMLocalDataSource @Inject constructor(
    private val manager: DatastoreManager
) {

    suspend fun saveCLoudMessagingToken(
        token: String
    ) {
        manager.putDataStoreString(DS_KEY_CLOUD_MESSAGING_TOKEN, token)
    }

    suspend fun clearCLoudMessagingToken() {
        manager.putDataStoreString(DS_KEY_CLOUD_MESSAGING_TOKEN, "")
    }

    suspend fun getCLoudMessagingToken(): String {
        return manager.getDataStoreString(DS_KEY_CLOUD_MESSAGING_TOKEN) ?: ""
    }

    companion object {
        private const val DS_KEY_CLOUD_MESSAGING_TOKEN = "cloud_messaging_token"
    }
}
