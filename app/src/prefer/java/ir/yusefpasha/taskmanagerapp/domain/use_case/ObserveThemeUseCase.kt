package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.entities.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveThemeUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<TaskThemeMode> {
        return taskRepository.observeTaskTheme()
    }

}