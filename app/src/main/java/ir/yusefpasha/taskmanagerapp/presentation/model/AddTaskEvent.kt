package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDateTime

@Immutable
sealed interface AddTaskEvent {

    object Apply : AddTaskEvent

    object NavigateUp : AddTaskEvent

    object ShowDateTimePicker : AddTaskEvent

    data class UpdateTitle(
        val title: String
    ) : AddTaskEvent

    data class UpdateDescription(
        val description: String
    ) : AddTaskEvent

    data class UpdateDeadline(
        val deadline: LocalDateTime
    ) : AddTaskEvent

}
