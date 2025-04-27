package ir.yusefpasha.taskmanagerapp.domain.utils

object Constants {

    const val DEFAULT_SHARING_START = 5_000L

    const val DATABASE_NAME = "app_db"
    const val DATABASE_VERSION = 1
    const val DATABASE_EXPORT_SCHEMA = true
    const val DATABASE_TASK_TABLE_NAME = "task"

    const val DATASTORE_NAME = "app_ds"
    const val DATASTORE_THEME_KEY = "theme"

    const val ALARM_SERVICE_ID_KEY = "id"
    const val ALARM_SERVICE_TITLE_KEY = "title"
    const val ALARM_SERVICE_SUBTITLE_KEY = "subtitle"

    const val NOTIFICATION_CHANNEL_ID = "Task Manager App"
    const val NOTIFICATION_CHANNEL_NAME = "Task Notification"
    const val NOTIFICATION_CHANNEL_DESCRIPTION =
        "Displaying notifications related to tasks, displays information about each task at the specified time"

    const val NETWORK_CLIENT_ERROR_MESSAGE = "Network Client Error!"
    const val NETWORK_SERVER_ERROR_MESSAGE = "Network Server Error!"
    const val NETWORK_TIMEOUT_ERROR_MESSAGE = "Network Timeout Error!"
    const val NETWORK_INTERNET_CONNECTION_ERROR_MESSAGE = "Network Internet Connection Error!"
    const val NETWORK_UNKNOWN_MESSAGE = "Unknown Error!"

}