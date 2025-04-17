package ir.yusefpasha.taskmanagerapp.data.di

import ir.yusefpasha.taskmanagerapp.data.local.TaskDao
import ir.yusefpasha.taskmanagerapp.data.local.TaskDatabase
import ir.yusefpasha.taskmanagerapp.data.remote.TaskApiService
import ir.yusefpasha.taskmanagerapp.data.repository.TaskRepositoryImpl
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseBuilder
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
    singleOf(::TaskApiService)
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
}
