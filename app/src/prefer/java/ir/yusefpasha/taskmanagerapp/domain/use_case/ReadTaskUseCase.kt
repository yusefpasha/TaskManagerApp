package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

class ReadTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: DatabaseId): Result<Task> {
        return try {
            val task = taskRepository.readTask(id = taskId)
            if (task == null) throw Exception("Task Not Found!")
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}