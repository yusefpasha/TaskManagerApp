package ir.yusefpasha.taskmanagerapp.data.network

import ir.yusefpasha.taskmanagerapp.data.network.dto.TaskDto
import ir.yusefpasha.taskmanagerapp.domain.utils.convertLocalDateTimeToMillisecond
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response
import retrofit2.http.GET
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

interface TaskApiService {

    @GET
    suspend fun fetchTasks(): Response<List<TaskDto>>

}