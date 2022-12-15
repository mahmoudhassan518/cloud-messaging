package com.mahmoud.cloudmessaging.presentation

import com.mahmoud.cloudmessaging.NotificationHandling
import com.mahmoud.cloudmessaging.presentation.viewmodel.CMServiceController
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class CloudMessagingService : HmsMessageService() {
    @Inject
    lateinit var notificationHandling: NotificationHandling

    @Inject
    lateinit var controller: CMServiceController


    override fun onCreate() {
        super.onCreate()

        notificationHandling.createNotificationChannels()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (notificationHandling.shouldShowNotification) {
            val notificationData =
                notificationHandling.provideNotificationRequiredData(dataPayloadMap = message.dataOfMap)
                    .copy(
                        title = message.notification?.title,
                        body = message.notification?.body,
                        notificationId = message.sentTime
                    )
            setUpNotification(
                notificationParam = notificationData,
                intent = notificationHandling.provideTargetIntent(),
                componentType = notificationHandling.provideTargetComponentType(),
                navigateToTargetIntent = notificationHandling.navigateToTargetIntent,
                notificationSmallIcon = notificationHandling.provideNotificationSmallIcon()
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
