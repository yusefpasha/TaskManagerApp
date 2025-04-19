package ir.yusefpasha.taskmanagerapp.domain.di

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
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val TaskDomainInjection = module {
    singleOf(::ReadTaskUseCase)
    singleOf(::CreateTaskUseCase)
    singleOf(::UpdateTaskUseCase)
    singleOf(::DeleteTaskUseCase)
    singleOf(::SyncTaskUseCase)
    singleOf(::ObserveTaskUseCase)
    singleOf(::ChangeThemeUseCase)
    singleOf(::ObserveThemeUseCase)
    singleOf(::StopSyncTaskUseCase)
    singleOf(::StartSyncTaskUseCase)
    singleOf(::ObserveSyncTaskUseCase)
}
