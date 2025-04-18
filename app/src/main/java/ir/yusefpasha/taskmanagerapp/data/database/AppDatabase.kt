package ir.yusefpasha.taskmanagerapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.yusefpasha.taskmanagerapp.data.database.dao.TaskDao
import ir.yusefpasha.taskmanagerapp.data.database.entity.TaskEntity
import ir.yusefpasha.taskmanagerapp.data.database.converter.LocalDateTimeConverter
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants

@Database(
    entities = [TaskEntity::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = Constants.DATABASE_EXPORT_SCHEMA
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}