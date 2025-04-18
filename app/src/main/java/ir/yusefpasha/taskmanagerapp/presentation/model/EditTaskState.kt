package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Stable
import ir.yusefpasha.taskmanagerapp.domain.utils.default
import kotlinx.datetime.LocalDateTime

@Stable
data class EditTaskState(
    val task: TaskItem? = null,
    val title: String = task?.title.orEmpty(),
    val description: String = task?.description.orEmpty(),
    val deadline: LocalDateTime = task?.deadline ?: LocalDateTime.default,
    val isLoading: Boolean = false
) {

    fun isValidForUpdate(): Boolean {
        return when {
            task == null -> false
            isLoading -> false
            title.isNotBlank() && title != task.title -> true
            description.isNotBlank() && description != task.description -> true
            deadline != task.deadline -> true
            else -> false
        }
    }

    fun isValidForDelete(): Boolean {
        return when {
            isLoading -> false
            else -> true
        }
    }

    fun isValidForCancel(): Boolean {
        return when {
            isLoading -> false
            else -> true
        }
    }

    fun isValidForNavigateUp(): Boolean = isValidForCancel()

}
