package ir.yusefpasha.taskmanagerapp.data.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import ir.yusefpasha.taskmanagerapp.data.receiver.AlarmReceiver;
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants;

public class AlarmService {
    private final Context context;
    private final AlarmManager alarmManager;

    public AlarmService(Context context, AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    public void cancel(long id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.cancel(pendingIntent);
    }

    @SuppressLint("ScheduleExactAlarm")
    public void schedule(long id, String title, String subtitle, long timestamp) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Constants.ALARM_SERVICE_ID_KEY, id);
        intent.putExtra(Constants.ALARM_SERVICE_TITLE_KEY, title);
        intent.putExtra(Constants.ALARM_SERVICE_SUBTITLE_KEY, subtitle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timestamp,
                        pendingIntent
                );
            }
        } else {
            alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timestamp,
                    pendingIntent
            );
        }
    }
}