package com.mahmoud.cloudmessaging.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationNavigationParam(val screen: String?, val id: String?) : Parcelable {
    companion object {
        const val FCM_NAVIGATION_PARAM = "notificationNavigationParam"
    }
}
