package ir.yusefpasha.taskmanagerapp.data.mapper

import ir.yusefpasha.taskmanagerapp.data.local.TaskEntity
import ir.yusefpasha.taskmanagerapp.data.remote.TaskDto
import ir.yusefpasha.taskmanagerapp.domain.model.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        deadline = deadline
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        deadline = deadline
    )
}

fun TaskDto.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        deadline = deadline
    )
}
