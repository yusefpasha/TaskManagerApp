package ir.yusefpasha.taskmanagerapp.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.yusefpasha.taskmanagerapp.data.service.NotificationServiceImpl
import ir.yusefpasha.taskmanagerapp.domain.service.NotificationService
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val extraId: Int = intent.getIntExtra(Constants.ALARM_SERVICE_ID_KEY, 0)
            val extraTitle: String = intent.getStringExtra(Constants.ALARM_SERVICE_TITLE_KEY) ?: "404!"
            val extraSubtitle: String = intent.getStringExtra(Constants.ALARM_SERVICE_SUBTITLE_KEY) ?: "404!"
            val notificationService: NotificationService = NotificationServiceImpl(context = context)
            notificationService.showSimple(
                id = extraId,
                title = extraTitle,
                subtitle = extraSubtitle
            )
        }
    }

}
