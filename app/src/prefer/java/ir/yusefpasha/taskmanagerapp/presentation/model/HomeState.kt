package ir.yusefpasha.taskmanagerapp.presentation.model

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable

@Stable
data class HomeState(
    val tasks: List<TaskItem> = emptyList(),
    val isLoading: Boolean = false,
    val tasksListState: LazyListState = LazyListState()
)
