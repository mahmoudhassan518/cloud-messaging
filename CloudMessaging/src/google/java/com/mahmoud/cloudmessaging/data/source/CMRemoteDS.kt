package com.mahmoud.cloudmessaging.data.source

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class CMRemoteDS @Inject constructor() {

    suspend fun getCMToken(): String = suspendCancellableCoroutine { continuation ->

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                continuation.resumeWithException(task.exception as Throwable)
            }
            // Get new FCM registration token
            val token = task.result

            continuation.resume(token ?: "", null)
        }
    }

    suspend fun unRegisterCMToken(): Unit = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().deleteToken()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit, null)
                } else {
                    continuation.resumeWithException(task.exception as Throwable)
                }
            }
    }
}
