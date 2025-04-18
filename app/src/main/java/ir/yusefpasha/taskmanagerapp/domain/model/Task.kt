package ir.yusefpasha.taskmanagerapp.domain.model

import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

data class Task(
    val id: DatabaseId,
    val title: String,
    val description: String,
    val deadline: Long,
)
