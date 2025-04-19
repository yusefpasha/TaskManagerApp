package ir.yusefpasha.taskmanagerapp.data.network

import android.util.Log
import ir.yusefpasha.taskmanagerapp.data.network.dto.TaskDto

class ApiService(private val apiService: TaskApiService) {

    suspend fun fetchTasks(): List<TaskDto> {
        val result = apiHandler { apiService.fetchTasks().body() }
        result.onFailure { Log.d("ApiService", "FetchTasks Error: ${it.message}") }
        return result.getOrNull().orEmpty()
    }

}