package ir.yusefpasha.taskmanagerapp.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun convertMillisecondToLocalDateTime(millis: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.currentSystemDefault())
}

fun convertLocalDateTimeToMillisecond(localDateTime: LocalDateTime): Long {
    return localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}
