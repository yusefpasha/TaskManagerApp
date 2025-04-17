package ir.yusefpasha.taskmanagerapp.presentation.model

import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.datetime.LocalDateTime

data class TaskItem(
    val id: DatabaseId,
    val title: String,
    val description: String,
    val deadline: LocalDateTime,
)
