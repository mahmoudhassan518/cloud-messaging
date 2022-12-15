package com.mahmoud.cloudmessaging.core

object CloudMessageUrlsConfig {

    val NOTIFICATION_API =
        if (CloudMessageBuildType.isDeBug()) "https://beta-notifications.com/PushNotification/"
        else "https://notifications.api.com/PushNotification/"
}
