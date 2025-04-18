package ir.yusefpasha.taskmanagerapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.coroutines.flow.Flow

private const val TABLE_NAME = Constants.TASK_TABLE_NAME

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun observeAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: TaskEntity): Long

    @Upsert
    suspend fun upsert(tasks: List<TaskEntity>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :taskId")
    suspend fun read(taskId: DatabaseId): TaskEntity?

    @Update
    suspend fun update(task: TaskEntity): Int

    @Delete
    suspend fun delete(task: TaskEntity): Int

    @Query("DELETE FROM $TABLE_NAME WHERE id = :taskId")
    suspend fun deleteById(taskId: DatabaseId): Int

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll(): Int

}