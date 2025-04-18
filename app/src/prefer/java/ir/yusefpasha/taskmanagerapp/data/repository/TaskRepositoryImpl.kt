package ir.yusefpasha.taskmanagerapp.data.repository

import ir.yusefpasha.taskmanagerapp.data.local.TaskDao
import ir.yusefpasha.taskmanagerapp.data.local.TaskDataStore
import ir.yusefpasha.taskmanagerapp.data.mapper.toTask
import ir.yusefpasha.taskmanagerapp.data.mapper.toTaskEntity
import ir.yusefpasha.taskmanagerapp.data.remote.TaskApiService
import ir.yusefpasha.taskmanagerapp.data.service.AlarmService
import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import ir.yusefpasha.taskmanagerapp.domain.utils.toDatabaseId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val alarmService: AlarmService,
    private val taskDataStore: TaskDataStore,
    private val taskApiService: TaskApiService,
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> {
        return taskDao.observeAllTasks().map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }
    }

    override suspend fun createTask(task: Task): Boolean {
        val id = taskDao.insert(task = task.toTaskEntity())
        val result = id > 0
        if (result) {
            alarmService.schedule(
                id = id.toDatabaseId(),
                title = task.title,
                subtitle = task.description,
                timestamp = task.deadline
            )
        }
        return result
    }

    override suspend fun readTask(id: DatabaseId): Task? {
        return taskDao.read(taskId = id)?.toTask()
    }

    override suspend fun updateTask(task: Task): Boolean {
        val result = taskDao.update(task = task.toTaskEntity()) > 0
        if (result) {
            alarmService.schedule(
                id = task.id,
                title = task.title,
                subtitle = task.description,
                timestamp = task.deadline
            )
        }
        return result
    }

    override suspend fun deleteTask(id: DatabaseId): Boolean {
        val result = taskDao.deleteById(taskId = id) > 0
        if (result) {
            alarmService.cancel(id = id)
        }
        return result
    }

    override suspend fun syncTask() {
        val remoteTasks = taskApiService.getTasks()
        val localTasks = taskDao.getAllTasks()
        val localTaskMap = localTasks.associateBy { it.id }

        remoteTasks.forEach { remoteTask ->
            val localTask = localTaskMap[remoteTask.id]
            if (localTask != null) {
                val updatedEntity = remoteTask.toTaskEntity()
                taskDao.update(updatedEntity)
            } else {
                val newEntity = remoteTask.toTaskEntity()
                taskDao.insert(newEntity)
            }
        }
    }

    override fun observeTaskTheme(): Flow<TaskThemeMode> {
        return taskDataStore.taskThemeFlow.map { isDarkTheme ->
            when (isDarkTheme) {
                null -> TaskThemeMode.Auto
                true -> TaskThemeMode.DarkMode
                false -> TaskThemeMode.LightMode
            }
        }
    }

    override suspend fun persistTaskTheme(theme: TaskThemeMode): Boolean {
        val result = taskDataStore.persistTaskTheme(
            data = when (theme) {
                TaskThemeMode.Auto -> null
                TaskThemeMode.DarkMode -> true
                TaskThemeMode.LightMode -> false
            }
        )
        return result
    }
}