package ir.yusefpasha.taskmanagerapp.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ir.yusefpasha.taskmanagerapp.BuildConfig
import ir.yusefpasha.taskmanagerapp.data.network.dto.TaskDto
import ir.yusefpasha.taskmanagerapp.domain.utils.convertLocalDateTimeToMillisecond
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

class ApiService(private val httpClient: HttpClient) {

    suspend fun fetchTasks(): List<TaskDto> {
        val result = apiHandler { httpClient.get(BuildConfig.BASE_URL).body<List<TaskDto>>() }
        result.onFailure { Log.d("ApiService", "Error Message: ${it.message}") }
        return result.getOrNull().orEmpty()
    }

}
