package ir.yusefpasha.taskmanagerapp.data.service

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.service.NotificationService
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.data.utils.NotificationBuilder
import ir.yusefpasha.taskmanagerapp.data.utils.PermissionFactory
import kotlin.random.Random

class NotificationServiceImpl(private val context: Context) : NotificationService {

    private val notificationManager = NotificationBuilder.NotificationManager(context)
        .setChannelId(Constants.NOTIFICATION_CHANNEL_ID)
        .setChannelName(Constants.NOTIFICATION_CHANNEL_NAME)
        .setChannelDescription(Constants.NOTIFICATION_CHANNEL_DESCRIPTION)
        .build()

    private val intentOpenApp = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
    }

    private val pendingIntentOpenApp: PendingIntent = PendingIntent.getActivity(
        context,
        Random.nextInt(),
        intentOpenApp,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun showSimple(
        id: Int,
        title: String,
        subtitle: String
    ) {

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSubText(Constants.NOTIFICATION_CHANNEL_NAME)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(subtitle)
            )
            .setContentIntent(pendingIntentOpenApp)
            .setColor(Color.Blue.toArgb())
            .setAutoCancel(true)
            .setColorized(true)
            .setVibrate(longArrayOf(2000, 1000, 500, 1000))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        show(id = id, notification = notification)
    }

    //region Helper

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun show(id: Int, notification: Notification) {
        if (PermissionFactory.checkPermissionGranted(context = context, *PermissionFactory.Group.NOTIFICATION)) {
            notificationManager.notify(id, notification)
        } else {
            Log.d("LEGZO", "Notification Permission Denied!")
        }
    }

    //endregion

}