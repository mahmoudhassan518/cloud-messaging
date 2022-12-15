package com.mahmoud.myapplication.notification.contracts

import android.content.Context
import com.mahmoud.cloudmessaging.contract.NotificationTokenContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationTokenContractImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationTokenContract {

    override suspend fun onRegisterToken(token: String) {
        // call the register token end point
    }

    override suspend fun onUnRegisterToken(token: String) {
        // call the un register token end point
    }
}
