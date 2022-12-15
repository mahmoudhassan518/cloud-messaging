package com.mahmoud.cloudmessaging.contract

import android.content.Intent
import com.mahmoud.cloudmessaging.presentation.model.NotificationParam

interface NotificationConfigurationsContract {

    fun createNotificationChannels()
    fun provideNotificationRequiredData(dataPayloadMap: Map<String, String>): NotificationParam
    fun provideTargetIntent(): Intent?
    fun provideTargetComponentType(): ComponentType = ComponentType.Activity
    fun provideNotificationSmallIcon(): Int
    var navigateToTargetIntent: Boolean
    var shouldShowNotification: Boolean

    enum class ComponentType { Service, Activity, Broadcast }
}
