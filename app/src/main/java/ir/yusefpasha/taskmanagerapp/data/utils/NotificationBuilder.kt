package ir.yusefpasha.taskmanagerapp.data.utils

import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

object NotificationBuilder {

    class NotificationManager(private val context: Context) {

        private lateinit var channelId: String
        private lateinit var channelName: String
        private lateinit var channelDescription: String

        fun setChannelId(channelId: String): NotificationManager {
            this.channelId = channelId
            return this
        }

        fun setChannelName(channelName: String): NotificationManager {
            this.channelName = channelName
            return this
        }

        fun setChannelDescription(channelDescription: String): NotificationManager {
            this.channelDescription = channelDescription
            return this
        }

        fun build(): NotificationManagerCompat {

            require(::channelId.isInitialized) {
                "Channel Id Not Init..."
            }

            require(::channelName.isInitialized) {
                "Channel Name Not Init..."
            }

            require(::channelDescription.isInitialized) {
                "Channel Description Not Init..."
            }

            val notificationManager = NotificationManagerCompat.from(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannelCompat.Builder(
                    channelId,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT
                )
                    .setName(channelName)
                    .setDescription(channelDescription)
                    .build()
                notificationManager.createNotificationChannelsCompat(listOf(channel))
            }

            return notificationManager
        }

    }

}