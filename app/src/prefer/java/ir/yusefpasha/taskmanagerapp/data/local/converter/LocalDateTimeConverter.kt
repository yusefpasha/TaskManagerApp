package ir.yusefpasha.taskmanagerapp.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime = LocalDateTime.parse(localDateTime)

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.toString()

}