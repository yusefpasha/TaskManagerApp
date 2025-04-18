package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository

class ChangeThemeUseCase(private val repository: TaskRepository) {

    suspend operator fun invoke(themeMode: TaskThemeMode) {
        repository.persistTaskTheme(theme = themeMode)
    }

}