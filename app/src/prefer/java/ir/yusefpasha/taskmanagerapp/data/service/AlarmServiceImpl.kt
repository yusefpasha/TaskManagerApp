package ir.yusefpasha.taskmanagerapp.data.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ir.yusefpasha.taskmanagerapp.data.receiver.AlarmReceiver
import ir.yusefpasha.taskmanagerapp.domain.service.AlarmService
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

class AlarmServiceImpl(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmService {

    override fun cancel(id: DatabaseId) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(
        id: DatabaseId,
        title: String,
        subtitle: String,
        timestamp: Long
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constants.ALARM_SERVICE_ID_KEY, id)
            putExtra(Constants.ALARM_SERVICE_TITLE_KEY, title)
            putExtra(Constants.ALARM_SERVICE_SUBTITLE_KEY, subtitle)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timestamp,
            pendingIntent
        )
    }

}