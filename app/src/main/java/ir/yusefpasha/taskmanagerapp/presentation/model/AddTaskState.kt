package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Stable
import ir.yusefpasha.taskmanagerapp.domain.utils.default
import kotlinx.datetime.LocalDateTime

@Stable
data class AddTaskState(
    val title: String = "",
    val description: String = "",
    val deadline: LocalDateTime = LocalDateTime.default,
    val isLoading: Boolean = false,
) {

    fun isValidForApply(): Boolean {
        return when {
            isLoading -> false
            title.isBlank() -> false
            description.isBlank() -> false
            deadline == LocalDateTime.default -> false
            else -> true
        }
    }

    fun isValidForCancel(): Boolean {
        return when {
            isLoading -> false
            else -> true
        }
    }

}
