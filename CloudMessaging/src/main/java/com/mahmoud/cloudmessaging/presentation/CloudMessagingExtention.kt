package com.mahmoud.cloudmessaging.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mahmoud.cloudmessaging.contract.NotificationConfigurationsContract
import com.mahmoud.cloudmessaging.presentation.model.NotificationNavigationParam
import com.mahmoud.cloudmessaging.presentation.model.NotificationNavigationParam.Companion.FCM_NAVIGATION_PARAM
import com.mahmoud.cloudmessaging.presentation.model.NotificationParam

@RequiresApi(Build.VERSION_CODES.O)
fun Context.createNotificationChannel(channelId: String, channelName: String) {
    val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager
    val assessmentChannel = NotificationChannel(
        channelId,
        channelName,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableVibration(true)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        setShowBadge(true)
    }
    notificationManager.createNotificationChannel(assessmentChannel)
}

fun Context.setUpNotification(
    notificationParam: NotificationParam,
    intent: Intent?,
    notificationSmallIcon: Int,
    navigateToTargetIntent: Boolean,
    componentType: NotificationConfigurationsContract.ComponentType
): Unit = with(notificationParam) {
    val pendingIntent = if (navigateToTargetIntent && intent != null) {
        generatePendingIntent(
            notificationParam = this,
            intent = intent,
            componentType = componentType
        )
    } else null

    if (title != null && body != null) {
        createNotification(
            title = title,
            message = body,
            groupId = "8",
            channelId = channelId,
            intent = pendingIntent,
            notificationSmallIcon = notificationSmallIcon

        )
    }
}

private fun Context.generatePendingIntent(
    notificationParam: NotificationParam,
    intent: Intent,
    componentType: NotificationConfigurationsContract.ComponentType
): PendingIntent {
    val notificationNavigationParam = NotificationNavigationParam(
        screen = notificationParam.screenName,
        id = notificationParam.id
    )
    val bundle = Bundle().apply {
        putParcelable(FCM_NAVIGATION_PARAM, notificationNavigationParam)
    }
    intent.apply {
        putExtras(bundle)
    }
    return when (componentType) {
        NotificationConfigurationsContract.ComponentType.Service -> PendingIntent.getService(
            this,
            notificationParam.hashCode(),
            intent,
            getPendingIntentFlags()
        )
        NotificationConfigurationsContract.ComponentType.Activity -> PendingIntent.getActivity(
            this,
            notificationParam.hashCode(),
            intent,
            getPendingIntentFlags()
        )
        NotificationConfigurationsContract.ComponentType.Broadcast -> PendingIntent.getBroadcast(
            this,
            notificationParam.hashCode(),
            intent,
            getPendingIntentFlags()
        )
    }
}

private fun getPendingIntentFlags(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
    }
}

fun Context.createNotification(
    title: String,
    message: String,
    groupId: String,
    channelId: String,
    intent: PendingIntent?,
    notificationSmallIcon: Int
) {
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val newNotification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(notificationSmallIcon)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(message)
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSound(defaultSoundUri)
        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        .setGroup(groupId)
        .setAutoCancel(true)
        .build()
    intent?.let { newNotification.contentIntent = it } ?: run {
        val pendingIntentWithoutIntent = PendingIntent.getActivity(
            this,
            message.hashCode(),
            Intent(),
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        newNotification.contentIntent = pendingIntentWithoutIntent
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        val groupNotificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationSmallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroup(groupId)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setGroupSummary(true)
            .build()
        NotificationManagerCompat.from(this).apply {
            notify(System.currentTimeMillis().toInt(), newNotification)
            notify(55, groupNotificationBuilder)
        }
    } else {
        NotificationManagerCompat.from(this).apply {
            notify(System.currentTimeMillis().toInt(), newNotification)
        }
    }
}

fun Context.clearAllNotifications() {
    val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager

    notificationManager.cancelAll()
}

private fun Context.getLauncherClassName(): String? {
    val pm = packageManager
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    val resolveInfos = pm.queryIntentActivities(intent, 0)
    for (resolveInfo: ResolveInfo in resolveInfos) {
        val pkgName = resolveInfo.activityInfo.applicationInfo.packageName
        if (pkgName == packageName) {
            return resolveInfo.activityInfo.name
        }
    }
    return null
}

fun Context.updateAppBadge(count: Int) {
    val launcherClassName: String = getLauncherClassName() ?: return

    val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE").apply {
        putExtra("badge_count", count)
        putExtra("badge_count_package_name", packageName)
        putExtra("badge_count_class_name", launcherClassName)
    }
    sendBroadcast(intent)
}
