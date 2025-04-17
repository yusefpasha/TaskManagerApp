package ir.yusefpasha.taskmanagerapp.domain.service

import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId

interface AlarmService {

    fun cancel(id: DatabaseId)

    fun schedule(
        id: DatabaseId,
        title: String,
        subtitle: String,
        timestamp: Long
    )

}