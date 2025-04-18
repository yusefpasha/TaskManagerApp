package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import ir.yusefpasha.taskmanagerapp.domain.entities.TaskThemeMode

@Stable
data class HomeState(
    val theme: TaskThemeMode = TaskThemeMode.Auto,
    val tasks: List<TaskItem> = emptyList(),
    val isLoading: Boolean = false,
    val tasksListState: LazyListState = LazyListState()
)
