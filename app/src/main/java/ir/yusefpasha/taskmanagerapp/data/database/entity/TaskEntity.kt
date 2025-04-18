package ir.yusefpasha.taskmanagerapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.domain.utils.DEFAULT_DATABASE_ID
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

@Entity(tableName = Constants.DATABASE_TASK_TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: DatabaseId = DEFAULT_DATABASE_ID,
    val title: String,
    val description: String,
    val deadline: Long
)
