package ir.yusefpasha.taskmanagerapp.data.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.yusefpasha.taskmanagerapp.data.service.NotificationService
import ir.yusefpasha.taskmanagerapp.data.utils.PermissionFactory
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission") // Android Studio... :/
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val extraId: Int = intent.getIntExtra(Constants.ALARM_SERVICE_ID_KEY, 0)
            val extraTitle: String = intent.getStringExtra(Constants.ALARM_SERVICE_TITLE_KEY) ?: "404!"
            val extraSubtitle: String = intent.getStringExtra(Constants.ALARM_SERVICE_SUBTITLE_KEY) ?: "404!"
            if (PermissionFactory.checkPermissionGranted(
                    context = context,
                    *PermissionFactory.Group.NOTIFICATION
                )
            ) {
                val notificationService = NotificationService(context = context)
                notificationService.showSimple(
                    id = extraId,
                    title = extraTitle,
                    subtitle = extraSubtitle
                )
            }
        }
    }

}
