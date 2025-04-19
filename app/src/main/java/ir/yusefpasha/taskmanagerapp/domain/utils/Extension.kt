package ir.yusefpasha.taskmanagerapp.domain.utils

import ir.yusefpasha.taskmanagerapp.domain.model.TimeRelation
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlin.time.Duration.Companion.hours

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

fun LocalDateTime.checkTimeRelation(
    currentClock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): TimeRelation {
    val nowInstant = currentClock.now()
    val targetInstant = this.toInstant(timeZone)

    val duration = targetInstant - nowInstant

    return when {
        duration.absoluteValue <= 2.hours -> TimeRelation.NEAR_NOW
        duration.isNegative() -> TimeRelation.BEFORE_NOW
        else -> TimeRelation.AFTER_NOW
    }
}