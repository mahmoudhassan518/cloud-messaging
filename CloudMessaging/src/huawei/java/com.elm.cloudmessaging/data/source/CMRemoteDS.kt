package com.mahmoud.cloudmessaging.data.source

import android.content.Context
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class CMRemoteDS @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun getCMToken(): String = suspendCancellableCoroutine { continuation ->
        val appId = AGConnectServicesConfig.fromContext(context)
            .getString("client/app_id")
        val token = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
        if (!token.isNullOrEmpty()) {
            continuation.resume(token, null)
        } else continuation.resumeWithException(Exception())
    }

    suspend fun unRegisterCMToken(): Unit = suspendCancellableCoroutine { continuation ->
        try {
            val appId = AGConnectServicesConfig.fromContext(context)
                .getString("client/app_id")
            HmsInstanceId.getInstance(context).deleteToken(appId, "HCM")
            continuation.resume(Unit, null)
        } catch (exception: Exception) {
            continuation.resumeWithException(exception as Throwable)
        }
    }
}
