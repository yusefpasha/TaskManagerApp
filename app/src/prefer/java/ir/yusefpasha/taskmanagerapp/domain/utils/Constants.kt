package ir.yusefpasha.taskmanagerapp.domain.utils

object Constants {

    const val DEFAULT_DELAY = 500L
    const val DEFAULT_SHARING_START = 5_000L

    const val TASK_TABLE_NAME = "task"
    const val TASK_DATABASE_NAME = "task_db"
    const val TASK_DATABASE_VERSION = 1
    const val TASK_DATABASE_EXPORT_SCHEMA = true

    const val ALARM_SERVICE_ID_KEY = "id"
    const val ALARM_SERVICE_TITLE_KEY = "title"
    const val ALARM_SERVICE_SUBTITLE_KEY = "subtitle"

    const val NOTIFICATION_CHANNEL_ID = "Task Manager App"
    const val NOTIFICATION_CHANNEL_NAME = "Task Deadline Notification"
    const val NOTIFICATION_CHANNEL_DESCRIPTION =
        "Displaying notifications related to tasks, displays information about each task at the specified time"

}