package ir.yusefpasha.taskmanagerapp.data.di

import android.app.AlarmManager
import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.yusefpasha.taskmanagerapp.BuildConfig
import ir.yusefpasha.taskmanagerapp.data.data_source.local.LocalDataSource
import ir.yusefpasha.taskmanagerapp.data.data_source.remote.RemoteDataSource
import ir.yusefpasha.taskmanagerapp.data.database.AppDatabase
import ir.yusefpasha.taskmanagerapp.data.database.dao.TaskDao
import ir.yusefpasha.taskmanagerapp.data.datastore.AppDataStore
import ir.yusefpasha.taskmanagerapp.data.network.ApiService
import ir.yusefpasha.taskmanagerapp.data.network.TaskApiService
import ir.yusefpasha.taskmanagerapp.data.repository.TaskRepositoryImpl
import ir.yusefpasha.taskmanagerapp.data.service.AlarmService
import ir.yusefpasha.taskmanagerapp.data.utils.DatabaseBuilder
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideRepository(
        repository: TaskRepositoryImpl
    ): TaskRepository

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides
        fun provideApiService(
            apiService: TaskApiService
        ): ApiService {
            return ApiService(apiService = apiService)
        }

        @Provides
        fun provideTaskApiService(
            retrofit: Retrofit
        ): TaskApiService {
            return retrofit.create(TaskApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase {
            return DatabaseBuilder.builder(
                context = context,
                databaseName = Constants.DATABASE_NAME
            )
        }

        @Provides
        @Singleton
        fun provideTaskDao(
            database: AppDatabase
        ): TaskDao {
            return database.taskDao()
        }

        @Provides
        @Singleton
        fun provideLocalDataSource(
            taskDao: TaskDao,
            appDataStore: AppDataStore
        ): LocalDataSource {
            return LocalDataSource(taskDao = taskDao, appDataStore = appDataStore)
        }

        @Provides
        @Singleton
        fun provideRemoteDataSource(
            apiService: ApiService
        ): RemoteDataSource {
            return RemoteDataSource(apiService = apiService)
        }

        @Provides
        @Singleton
        fun provideWorkManager(
            @ApplicationContext context: Context
        ): WorkManager {
            return WorkManager.getInstance(context = context)
        }

        @Provides
        @Singleton
        fun provideAlarmManager(
            @ApplicationContext context: Context
        ): AlarmManager {
            return context.getSystemService(AlarmManager::class.java)
        }

        @Provides
        @Singleton
        fun provideAlarmService(
            @ApplicationContext context: Context,
            alarmManager: AlarmManager
        ): AlarmService {
            return AlarmService(
                context = context,
                alarmManager = alarmManager
            )
        }

        @Provides
        @Singleton
        fun provideDatastore(
            @ApplicationContext context: Context
        ): AppDataStore {
            return AppDataStore(context = context)
        }

        @Provides
        @Singleton
        fun provideRepositoryImpl(
            workManager: WorkManager,
            alarmService: AlarmService,
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
        ): TaskRepositoryImpl {
            return TaskRepositoryImpl(
                workManager = workManager,
                alarmService = alarmService,
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
            )
        }

    }

}
