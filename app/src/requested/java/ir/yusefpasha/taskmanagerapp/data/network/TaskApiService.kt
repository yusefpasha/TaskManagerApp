package ir.yusefpasha.taskmanagerapp.data.network

import ir.yusefpasha.taskmanagerapp.data.network.dto.TaskDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface TaskApiService {

    @GET
    suspend fun fetchTasks(@Url baseUrl: String = ""): Response<List<TaskDto>>

}