package ir.yusefpasha.taskmanagerapp.data.data_source.remote

import ir.yusefpasha.taskmanagerapp.data.network.ApiService
import ir.yusefpasha.taskmanagerapp.data.network.dto.TaskDto

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getTasks(): List<TaskDto> {
        return apiService.fetchTasks()
    }

}
