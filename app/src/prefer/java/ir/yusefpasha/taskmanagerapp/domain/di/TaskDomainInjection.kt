package ir.yusefpasha.taskmanagerapp.domain.di

import ir.yusefpasha.taskmanagerapp.domain.use_case.CreateTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.DeleteTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ReadTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.RefreshTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.UpdateTaskUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val TaskDomainInjection = module {
    singleOf(::RefreshTaskUseCase)
    singleOf(::CreateTaskUseCase)
    singleOf(::ReadTaskUseCase)
    singleOf(::UpdateTaskUseCase)
    singleOf(::DeleteTaskUseCase)
    singleOf(::ObserveTaskUseCase)
}
