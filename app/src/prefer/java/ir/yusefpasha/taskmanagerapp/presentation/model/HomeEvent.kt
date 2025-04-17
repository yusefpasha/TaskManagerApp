package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Immutable
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

@Immutable
sealed interface HomeEvent {

    object Exit : HomeEvent

    object RefreshTask : HomeEvent

    data class NavigateToTask(
        val taskId: DatabaseId
    ) : HomeEvent

    object NavigateToAddTask : HomeEvent

    data class NavigateToEditTask(
        val taskId: DatabaseId
    ) : HomeEvent

}
