package ir.yusefpasha.taskmanagerapp

import android.app.Application
import ir.yusefpasha.taskmanagerapp.data.di.TaskDataInjection
import ir.yusefpasha.taskmanagerapp.domain.di.TaskDomainInjection
import ir.yusefpasha.taskmanagerapp.presentation.di.TaskPresentationInjection
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                TaskDataInjection,
                TaskDomainInjection,
                TaskPresentationInjection,
            )
        }
    }

}