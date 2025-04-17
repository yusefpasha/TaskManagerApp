package ir.yusefpasha.taskmanagerapp.domain.utils

import kotlinx.datetime.LocalDateTime

val LocalDateTime.Companion.default: LocalDateTime
    get() {
        return LocalDateTime(1, 1, 1, 1, 1, 1)
    }

fun LocalDateTime.convertToDisplayText(): String {
    return buildString {
        append(this@convertToDisplayText.year)
        append("/")
        append(this@convertToDisplayText.monthNumber)
        append("/")
        append(this@convertToDisplayText.dayOfMonth)

        append("  ")

        append(this@convertToDisplayText.hour)
        append(":")
        append(this@convertToDisplayText.minute)
    }
}