package ir.yusefpasha.taskmanagerapp.data.di

import android.app.AlarmManager
import androidx.work.WorkManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import ir.yusefpasha.taskmanagerapp.data.data_source.local.LocalDataSource
import ir.yusefpasha.taskmanagerapp.data.data_source.remote.RemoteDataSource
import ir.yusefpasha.taskmanagerapp.data.database.AppDatabase
import ir.yusefpasha.taskmanagerapp.data.database.dao.TaskDao
import ir.yusefpasha.taskmanagerapp.data.datastore.AppDataStore
import ir.yusefpasha.taskmanagerapp.data.network.ApiService
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
    single<AppDatabase> {
        val context = androidContext()
        DatabaseBuilder.builder<AppDatabase>(
            context = context,
            databaseName = Constants.DATABASE_NAME
        )
    }
    single<TaskDao> { get<AppDatabase>().taskDao() }
    single<AlarmManager> {
        val context = androidContext()
        context.getSystemService(AlarmManager::class.java)
    }
    single<WorkManager> {
        val context = androidContext()
        WorkManager.getInstance(context = context)
    }
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    singleOf(::ApiService)
    singleOf(::AppDataStore)
    singleOf(::AlarmService)
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
    singleOf(::NotificationService) { bind<NotificationService>() }

    workerOf(::SyncTaskService)
}
