package ir.yusefpasha.taskmanagerapp.presentation.di

import ir.yusefpasha.taskmanagerapp.presentation.view_model.AddTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.EditTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val TaskPresentationInjection = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTaskViewModel)
    viewModelOf(::EditTaskViewModel)
}
