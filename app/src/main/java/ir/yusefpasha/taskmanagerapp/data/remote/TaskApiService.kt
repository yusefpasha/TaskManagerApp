package ir.yusefpasha.taskmanagerapp.data.remote

import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class TaskApiService {

    suspend fun getTasks(): List<TaskDto> {
        delay(Random.nextInt(from = 500, until = 1000).milliseconds)
        return emptyList()
    }

}