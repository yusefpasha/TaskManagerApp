package ir.yusefpasha.taskmanagerapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.use_case.ChangeThemeUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.CreateTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.DeleteTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveThemeUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ReadTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.StartSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.StopSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.SyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.UpdateTaskUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideChangeThemeUseCase(repository: TaskRepository): ChangeThemeUseCase {
        return ChangeThemeUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideCreateTaskUseCase(repository: TaskRepository): CreateTaskUseCase {
        return CreateTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideObserveSyncTaskUseCase(repository: TaskRepository): ObserveSyncTaskUseCase {
        return ObserveSyncTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideObserveTaskUseCase(repository: TaskRepository): ObserveTaskUseCase {
        return ObserveTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideObserveThemeUseCase(repository: TaskRepository): ObserveThemeUseCase {
        return ObserveThemeUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideReadTaskUseCase(repository: TaskRepository): ReadTaskUseCase {
        return ReadTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideStartSyncTaskUseCase(repository: TaskRepository): StartSyncTaskUseCase {
        return StartSyncTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideStopSyncTaskUseCase(repository: TaskRepository): StopSyncTaskUseCase {
        return StopSyncTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideSyncTaskUseCase(repository: TaskRepository): SyncTaskUseCase {
        return SyncTaskUseCase(taskRepository = repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(taskRepository = repository)
    }

}
