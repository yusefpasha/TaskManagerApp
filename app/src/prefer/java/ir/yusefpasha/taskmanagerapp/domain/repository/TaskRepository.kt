package ir.yusefpasha.taskmanagerapp.domain.repository

import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun observeTasks(): Flow<List<Task>>

    suspend fun createTask(task: Task): Boolean
    suspend fun readTask(id: DatabaseId): Task?
    suspend fun updateTask(task: Task): Boolean
    suspend fun deleteTask(id: DatabaseId): Boolean

    suspend fun syncTask()

    fun observeTaskTheme(): Flow<TaskThemeMode>
    suspend fun persistTaskTheme(theme: TaskThemeMode): Boolean

}