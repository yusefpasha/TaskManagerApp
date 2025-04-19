package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.runtime.Immutable
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

@Immutable
sealed interface HomeEvent {

    object Exit : HomeEvent

    object RefreshTask : HomeEvent

    data class ChangeTheme(
        val themeMode: TaskThemeMode
    ) : HomeEvent

    data class ExpandedMenu(
        val expanded: Boolean
    ) : HomeEvent

    data class SyncTask(
        val enable: Boolean
    ) : HomeEvent

    data class EditTask(
        val taskId: DatabaseId
    ) : HomeEvent

    data class DeleteTask(
        val taskId: DatabaseId
    ) : HomeEvent

    object NavigateToAddTask : HomeEvent

}
