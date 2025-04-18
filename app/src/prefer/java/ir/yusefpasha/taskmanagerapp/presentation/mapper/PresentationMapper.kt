package ir.yusefpasha.taskmanagerapp.presentation.mapper

import ir.yusefpasha.taskmanagerapp.domain.model.Task
import ir.yusefpasha.taskmanagerapp.domain.utils.convertMillisecondToLocalDateTime
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem

fun Task.toTaskItem(): TaskItem {
    return TaskItem(
        id = id,
        title = title,
        description = description,
        deadline = convertMillisecondToLocalDateTime(deadline)
    )
}
