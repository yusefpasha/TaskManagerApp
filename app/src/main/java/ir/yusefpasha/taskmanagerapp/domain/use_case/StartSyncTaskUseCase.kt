package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository

class StartSyncTaskUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke() {
        taskRepository.startSyncTaskScheduler()
    }

}