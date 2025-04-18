package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.model.UiMessage
import ir.yusefpasha.taskmanagerapp.domain.model.UiText
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        id: DatabaseId,
        title: String,
        description: String,
        deadline: Long,
    ): Result<UiMessage> {
        return try {
            val task = Task(
                id = id,
                title = title,
                description = description,
                deadline = deadline
            )
            val result = taskRepository.updateTask(task = task)
            if (!result) throw Exception("Unknown Error in $this")
            Result.success(UiMessage(text = UiText.ResourceString(R.string.update_task_success)))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}