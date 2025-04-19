package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import ir.yusefpasha.taskmanagerapp.domain.model.SyncTaskState
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode

@Stable
data class HomeState(
    val theme: TaskThemeMode = TaskThemeMode.Auto,
    val tasks: List<TaskItem> = emptyList(),
    val isLoading: Boolean = false,
    val syncTask: SyncTaskState = SyncTaskState.Unknown,
    val expandedMenu: Boolean = false,
    val tasksListState: LazyListState = LazyListState(),
) {

    fun syncTaskIsEnabled(): Boolean {
        return when (syncTask) {
            SyncTaskState.Unknown,
            SyncTaskState.Failure -> false

            SyncTaskState.Enqueued,
            SyncTaskState.Running,
            SyncTaskState.Success -> true
        }
    }

    fun taskGestureEnabled(): Boolean {
        return tasksListState.isScrollInProgress.not()
    }

}
