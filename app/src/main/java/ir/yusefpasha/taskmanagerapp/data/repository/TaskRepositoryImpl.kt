package ir.yusefpasha.taskmanagerapp.data.repository

import androidx.work.WorkInfo
import androidx.work.WorkManager
import ir.yusefpasha.taskmanagerapp.data.data_source.local.LocalDataSource
import ir.yusefpasha.taskmanagerapp.data.data_source.remote.RemoteDataSource
import ir.yusefpasha.taskmanagerapp.data.mapper.toTask
import ir.yusefpasha.taskmanagerapp.data.mapper.toTaskEntity
import ir.yusefpasha.taskmanagerapp.data.service.AlarmService
import ir.yusefpasha.taskmanagerapp.data.service.SyncTaskService
import ir.yusefpasha.taskmanagerapp.domain.model.SyncTaskState
import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val workManager: WorkManager,
    private val alarmService: AlarmService,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> {
        return localDataSource.observeTasks().map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }
    }

    override suspend fun createTask(task: Task): Boolean {
        return try {
            val id = localDataSource.createTask(task = task.toTaskEntity())
            alarmService.schedule(
                id.toLong(),
                task.title,
                task.description,
                task.deadline
            )
            true
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun readTask(id: DatabaseId): Task? {
        return localDataSource.readTask(id = id)?.toTask()
    }

    override suspend fun updateTask(task: Task): Boolean {
        val result = localDataSource.updateTask(task = task.toTaskEntity())
        if (result) {
            alarmService.schedule(
                task.id.toLong(),
                task.title,
                task.description,
                task.deadline
            )
        }
        return result
    }

    override suspend fun deleteTask(id: DatabaseId): Boolean {
        val result = localDataSource.deleteTask(id = id)
        if (result) {
            alarmService.cancel(id.toLong())
        }
        return result
    }

    override fun observeTaskTheme(): Flow<TaskThemeMode> {
        return localDataSource.observeTaskTheme().map { isDarkTheme ->
            when (isDarkTheme) {
                null -> TaskThemeMode.Auto
                true -> TaskThemeMode.DarkMode
                false -> TaskThemeMode.LightMode
            }
        }
    }

    override suspend fun persistTaskTheme(theme: TaskThemeMode): Boolean {
        val result = localDataSource.persistTaskTheme(
            data = when (theme) {
                TaskThemeMode.Auto -> null
                TaskThemeMode.DarkMode -> true
                TaskThemeMode.LightMode -> false
            }
        )
        return result
    }

    override suspend fun syncTask(): Boolean {
        val remoteTasks = remoteDataSource.getTasks()
        val result = localDataSource.upsertTasks(tasks = remoteTasks.map { it.toTaskEntity() })
        return result
    }

    override fun stopSyncTaskScheduler() {
        SyncTaskService.cancelWorkManager(workManager = workManager)
    }

    override fun startSyncTaskScheduler() {
        SyncTaskService.setupPeriodicWorkManager(workManager = workManager)
    }

    override fun observeSyncTaskScheduler(): Flow<SyncTaskState> {
        return workManager.getWorkInfosByTagFlow(SyncTaskService.TAG).map { workInfos ->
            workInfos.firstOrNull().let { workInfo ->
                when (workInfo?.state) {
                    WorkInfo.State.ENQUEUED -> SyncTaskState.Enqueued
                    WorkInfo.State.RUNNING -> SyncTaskState.Running
                    WorkInfo.State.SUCCEEDED -> SyncTaskState.Success
                    WorkInfo.State.FAILED,
                    WorkInfo.State.BLOCKED,
                    WorkInfo.State.CANCELLED -> SyncTaskState.Failure

                    null -> SyncTaskState.Unknown
                }
            }
        }
    }

}
