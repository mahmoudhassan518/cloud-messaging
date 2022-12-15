package com.mahmoud.cloudmessaging.presentation

import com.mahmoud.cloudmessaging.contract.NotificationConfigurationsContract
import com.mahmoud.cloudmessaging.presentation.viewmodel.CMServiceController
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CloudMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationConfigurationsContract: NotificationConfigurationsContract

    @Inject
    lateinit var controller: CMServiceController

    override fun onCreate() {
        super.onCreate()

        notificationConfigurationsContract.createNotificationChannels()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (notificationConfigurationsContract.shouldShowNotification) {
            val notificationData =
                notificationConfigurationsContract.provideNotificationRequiredData(dataPayloadMap = message.data)
                    .copy(
                        title = message.notification?.title,
                        body = message.notification?.body,
                        notificationId = message.sentTime
                    )
            setUpNotification(
                notificationParam = notificationData,
                intent = notificationConfigurationsContract.provideTargetIntent(),
                componentType = notificationConfigurationsContract.provideTargetComponentType(),
                navigateToTargetIntent = notificationConfigurationsContract.navigateToTargetIntent,
                notificationSmallIcon = notificationConfigurationsContract.provideNotificationSmallIcon()
            )
        }
    }

    override fun onNewToken(token: String) {
        controller.updateFcmToken(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}

// f3qNq0OYR7avLkY57miDh1:APA91bHnqPkZh4zw8Y1e5zRuFr7YBReJYpZmYeRchsLH7OTPbdRh-SDJn5hGUPXP0_N3L5nmQt_zsNE3cNoAkJYrQ35mYQkO_Vx9VsZ-c--UFeMhB7zmNjuah-2AGx-VjiIH9YPPVI39