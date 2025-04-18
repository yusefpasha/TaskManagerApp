package ir.yusefpasha.taskmanagerapp.data.mapper

import ir.yusefpasha.taskmanagerapp.data.local.TaskEntity
import ir.yusefpasha.taskmanagerapp.data.remote.TaskDto
import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.utils.DEFAULT_DATABASE_ID

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
        id = DEFAULT_DATABASE_ID,
        title = title,
        description = description,
        deadline = deadline
    )
}
