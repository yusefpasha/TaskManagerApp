package ir.yusefpasha.taskmanagerapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val title: String,
    val description: String,
    val deadline: Long
)
