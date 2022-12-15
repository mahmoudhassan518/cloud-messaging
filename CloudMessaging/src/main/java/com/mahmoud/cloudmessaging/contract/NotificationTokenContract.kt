package com.mahmoud.cloudmessaging.contract

interface NotificationTokenContract {

    suspend fun onRegisterToken(token: String)
    suspend fun onUnRegisterToken(token: String)
}
