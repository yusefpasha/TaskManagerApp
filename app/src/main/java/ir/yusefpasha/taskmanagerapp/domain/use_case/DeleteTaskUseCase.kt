package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.entities.UiMessage
import ir.yusefpasha.taskmanagerapp.domain.entities.UiText
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: DatabaseId): Result<UiMessage> {
        return try {
            val result = taskRepository.deleteTask(id = taskId)
            if (!result) throw Exception("Unknown Error in $this")
            Result.success(UiMessage(text = UiText.ResourceString(R.string.delete_task_success)))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}