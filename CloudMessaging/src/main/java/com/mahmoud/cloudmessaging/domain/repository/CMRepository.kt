package com.mahmoud.cloudmessaging.domain.repository

interface CMRepository {

    suspend fun registerCMToken()
    suspend fun unRegisterCMToken()
    suspend fun updateCMToken(token: String)
}
