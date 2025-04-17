package ir.yusefpasha.taskmanagerapp.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun convertMillisecondToLocalDateTime(
    millis: Long,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone)
}

fun convertLocalDateTimeToMillisecond(
    localDateTime: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {
    return localDateTime.toInstant(timeZone).toEpochMilliseconds()
}

fun Long.toDatabaseId(): DatabaseId = this.toInt()
