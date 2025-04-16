package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.entities.Task
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveTaskUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.observeTasks()
    }

}