package ir.yusefpasha.taskmanagerapp.data.repository

import ir.yusefpasha.taskmanagerapp.data.local.TaskDao
import ir.yusefpasha.taskmanagerapp.data.mapper.toTask
import ir.yusefpasha.taskmanagerapp.data.mapper.toTaskEntity
import ir.yusefpasha.taskmanagerapp.data.remote.TaskApiService
import ir.yusefpasha.taskmanagerapp.domain.entities.Task
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val taskApiService: TaskApiService
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
        return id > 0
    }

    override suspend fun readTask(id: DatabaseId): Task? {
        return taskDao.read(taskId = id).toTask()
    }

    override suspend fun updateTask(task: Task): Boolean {
        return taskDao.update(task = task.toTaskEntity()) > 0
    }

    override suspend fun deleteTask(id: DatabaseId): Boolean {
        return taskDao.deleteById(taskId = id) > 0
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

}