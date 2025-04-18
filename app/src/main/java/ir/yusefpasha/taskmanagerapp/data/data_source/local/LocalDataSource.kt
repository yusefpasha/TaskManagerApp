package ir.yusefpasha.taskmanagerapp.data.data_source.local

import ir.yusefpasha.taskmanagerapp.data.database.dao.TaskDao
import ir.yusefpasha.taskmanagerapp.data.database.entity.TaskEntity
import ir.yusefpasha.taskmanagerapp.data.datastore.AppDataStore
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import ir.yusefpasha.taskmanagerapp.domain.utils.toDatabaseId
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val taskDao: TaskDao,
    private val appDataStore: AppDataStore
) {

    fun observeTasks(): Flow<List<TaskEntity>> {
        return taskDao.observeAllTasks()
    }

    suspend fun createTask(task: TaskEntity): DatabaseId {
        val id = taskDao.insert(task = task)
        return id.toDatabaseId()
    }

    suspend fun readTask(id: DatabaseId): TaskEntity? {
        return taskDao.read(taskId = id)
    }

    suspend fun updateTask(task: TaskEntity): Boolean {
        val result = taskDao.update(task = task) > 0
        return result
    }

    suspend fun upsertTasks(tasks: List<TaskEntity>): Boolean {
        val result = taskDao.upsert(tasks = tasks).isNotEmpty()
        return result
    }

    suspend fun deleteTask(id: DatabaseId): Boolean {
        val result = taskDao.deleteById(taskId = id) > 0
        return result
    }

    // ---

    fun observeTaskTheme(): Flow<Boolean?> {
        return appDataStore.taskThemeFlow
    }

    suspend fun persistTaskTheme(data: Boolean?): Boolean {
        val result = appDataStore.persistTaskTheme(
            data = data
        )
        return result
    }

}