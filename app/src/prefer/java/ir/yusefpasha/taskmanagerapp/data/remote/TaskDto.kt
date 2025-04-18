package ir.yusefpasha.taskmanagerapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val title: String,
    val description: String,
    val deadline: Long
)
