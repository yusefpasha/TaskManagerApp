package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.entities.Task
import ir.yusefpasha.taskmanagerapp.domain.entities.UiMessage
import ir.yusefpasha.taskmanagerapp.domain.entities.UiText
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task): Result<UiMessage> {
        return try {
            val result = taskRepository.createTask(task = task)
            if (!result) throw Exception("Unknown Error in $this")
            Result.success(UiMessage(text = UiText.ResourceString(R.string.create_task_success)))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}