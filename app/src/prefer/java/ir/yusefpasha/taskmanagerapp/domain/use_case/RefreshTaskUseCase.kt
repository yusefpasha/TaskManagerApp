package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository

class RefreshTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke() {
        return taskRepository.syncTask()
    }

}