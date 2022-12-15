package com.mahmoud.cloudmessaging.presentation.model

data class NotificationParam(
    val title: String? = null,
    val body: String? = null,

    var screenName: String? = null,
    var id: String? = null,

    val notificationId: Long? = null,
    var channelId: String = ""

)
