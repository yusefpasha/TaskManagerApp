package ir.yusefpasha.taskmanagerapp.data.remote

import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: DatabaseId,
    val title: String,
    val description: String,
    val deadline: Long
)
