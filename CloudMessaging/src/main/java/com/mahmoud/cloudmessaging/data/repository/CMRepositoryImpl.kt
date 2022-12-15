package com.mahmoud.cloudmessaging.data.repository

import com.mahmoud.cloudmessaging.contract.NotificationTokenContract
import com.mahmoud.cloudmessaging.data.source.CMLocalDataSource
import com.mahmoud.cloudmessaging.data.source.CMRemoteDS
import com.mahmoud.cloudmessaging.domain.repository.CMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CMRepositoryImpl @Inject constructor(
    private val cmRemoteDS: CMRemoteDS,
    private val cmDataSource: CMLocalDataSource,
    private val notificationTokenContract: NotificationTokenContract

) : CMRepository {

    override suspend fun registerCMToken() = withContext(Dispatchers.IO) {
        val token = cmRemoteDS.getCMToken()
        updateCMToken(token)
    }

    override suspend fun unRegisterCMToken() =
        withContext(Dispatchers.IO) {
            val token = cmDataSource.getCLoudMessagingToken()
            notificationTokenContract.onUnRegisterToken(token)

            cmRemoteDS.unRegisterCMToken()

            cmDataSource.clearCLoudMessagingToken()
        }

    override suspend fun updateCMToken(token: String) =
        withContext(Dispatchers.IO) {
            notificationTokenContract.onRegisterToken(token)
            cmDataSource.saveCLoudMessagingToken(
                token = token
            )
        }
}
