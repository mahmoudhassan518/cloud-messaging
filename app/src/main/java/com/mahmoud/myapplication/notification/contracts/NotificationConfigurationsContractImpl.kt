package com.mahmoud.myapplication.notification.contracts

import android.content.Context
import android.content.Intent
import android.os.Build
import com.mahmoud.cloudmessaging.contract.NotificationConfigurationsContract
import com.mahmoud.cloudmessaging.presentation.createNotificationChannel
import com.mahmoud.cloudmessaging.presentation.model.NotificationParam
import com.mahmoud.myapplication.MainActivity
import com.mahmoud.myapplication.R
import com.mahmoud.myapplication.notification.presentation.model.DataPayload
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationConfigurationsContractImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Gson
) : NotificationConfigurationsContract {

    override var shouldShowNotification = true

    override var navigateToTargetIntent = true

    override fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.createNotificationChannel(
                NOTIFICATION_TEST_PAYMENT_CHANNEL_ID,
                NOTIFICATION_TEST_PAYMENT_CHANNEL_NAME
            )
        }
    }

    override fun provideNotificationRequiredData(dataPayloadMap: Map<String, String>): NotificationParam {
        val dataPayload = json.fromJson(
            dataPayloadMap.toString(),
            DataPayload::class.java
        )
        return NotificationParam(
            channelId = NOTIFICATION_TEST_PAYMENT_CHANNEL_ID,
            id = dataPayload.id,
            screenName = dataPayload.screenName
        )
    }

    override fun provideNotificationSmallIcon(): Int {
        return R.mipmap.ic_launcher
    }

    override fun provideTargetIntent() =
        Intent(context, MainActivity::class.java)

    companion object {
        private const val NOTIFICATION_TEST_PAYMENT_CHANNEL_ID = "2"
        private const val NOTIFICATION_TEST_PAYMENT_CHANNEL_NAME = "App_Payment"
    }
}
