package ir.yusefpasha.taskmanagerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.yusefpasha.taskmanagerapp.data.local.converter.LocalDateTimeConverter
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants

@Database(
    entities = [TaskEntity::class],
    version = Constants.TASK_DATABASE_VERSION,
    exportSchema = Constants.TASK_DATABASE_EXPORT_SCHEMA
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}