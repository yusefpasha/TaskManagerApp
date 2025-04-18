package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDateTime

@Immutable
sealed interface EditTaskEvent {

    object Delete : EditTaskEvent
    object Update : EditTaskEvent
    object ShowDateTimePicker : EditTaskEvent

    object NavigateUp : EditTaskEvent

    data class EditTitle(
        val title: String
    ) : EditTaskEvent

    data class EditDescription(
        val description: String
    ) : EditTaskEvent

    data class EditDeadline(
        val deadline: LocalDateTime
    ) : EditTaskEvent

}
