package ir.yusefpasha.taskmanagerapp.data.di

import android.app.AlarmManager
import ir.yusefpasha.taskmanagerapp.data.local.TaskDao
import ir.yusefpasha.taskmanagerapp.data.local.TaskDataStore
import ir.yusefpasha.taskmanagerapp.data.local.TaskDatabase
import ir.yusefpasha.taskmanagerapp.data.remote.TaskApiService
import ir.yusefpasha.taskmanagerapp.data.repository.TaskRepositoryImpl
import ir.yusefpasha.taskmanagerapp.data.service.AlarmServiceImpl
import ir.yusefpasha.taskmanagerapp.data.service.NotificationServiceImpl
import ir.yusefpasha.taskmanagerapp.data.utils.DatabaseBuilder
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.service.AlarmService
import ir.yusefpasha.taskmanagerapp.domain.service.NotificationService
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import org.koin.android.ext.koin.androidContext
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
    singleOf(::TaskDataStore)
    singleOf(::TaskApiService)
    singleOf(::AlarmServiceImpl) { bind<AlarmService>() }
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
    singleOf(::NotificationServiceImpl) { bind<NotificationService>() }
}
