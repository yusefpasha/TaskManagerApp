package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.entities.Task
import ir.yusefpasha.taskmanagerapp.domain.entities.UiMessage
import ir.yusefpasha.taskmanagerapp.domain.entities.UiText
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import ir.yusefpasha.taskmanagerapp.domain.utils.DEFAULT_DATABASE_ID

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        deadline: Long,
    ): Result<UiMessage> {
        return try {
            val task = Task(
                id = DEFAULT_DATABASE_ID,
                title = title,
                description = description,
                deadline = deadline
            )
            val result = taskRepository.createTask(task = task)
            if (!result) throw Exception("Unknown Error in $this")
            Result.success(UiMessage(text = UiText.ResourceString(R.string.create_task_success)))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}