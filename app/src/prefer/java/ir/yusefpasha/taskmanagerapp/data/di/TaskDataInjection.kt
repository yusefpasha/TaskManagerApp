package ir.yusefpasha.taskmanagerapp.data.di

import android.app.AlarmManager
import androidx.work.WorkManager
import ir.yusefpasha.taskmanagerapp.data.local.TaskDao
import ir.yusefpasha.taskmanagerapp.data.local.TaskDataStore
import ir.yusefpasha.taskmanagerapp.data.local.TaskDatabase
import ir.yusefpasha.taskmanagerapp.data.remote.TaskApiService
import ir.yusefpasha.taskmanagerapp.data.repository.TaskRepositoryImpl
import ir.yusefpasha.taskmanagerapp.data.service.AlarmService
import ir.yusefpasha.taskmanagerapp.data.service.NotificationService
import ir.yusefpasha.taskmanagerapp.data.service.SyncTaskService
import ir.yusefpasha.taskmanagerapp.data.utils.DatabaseBuilder
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val TaskDataInjection = module {
    single<TaskDatabase> {
        val context = androidContext()
        DatabaseBuilder.builder<TaskDatabase>(
            context = context,
            databaseName = Constants.TASK_DATABASE_NAME
        )
    }
    single<TaskDao> { get<TaskDatabase>().taskDao() }
    single<AlarmManager> {
        val context = androidContext()
        context.getSystemService(AlarmManager::class.java)
    }
    single<WorkManager> {
        val context = androidContext()
        WorkManager.getInstance(context = context)
    }
    singleOf(::AlarmService)
    singleOf(::TaskDataStore)
    singleOf(::TaskApiService)
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
    singleOf(::NotificationService) { bind<NotificationService>() }
    workerOf(::SyncTaskService)

}
