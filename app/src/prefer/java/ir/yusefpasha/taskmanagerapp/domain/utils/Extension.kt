package ir.yusefpasha.taskmanagerapp.domain.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

val LocalDateTime.Companion.default: LocalDateTime
    get() {
        return LocalDateTime(1, 1, 1, 1, 1, 1)
    }

fun LocalDateTime.convertToDisplayText(): String {
    val format = LocalDateTime.Format {
        year()
        char('/')
        monthNumber()
        char('/')
        dayOfMonth()
        char(' ')
        hour()
        char(':')
        minute()
    }
    return this.format(format)
}
