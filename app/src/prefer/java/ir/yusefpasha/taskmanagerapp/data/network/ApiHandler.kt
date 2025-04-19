package ir.yusefpasha.taskmanagerapp.data.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.net.ConnectException

suspend fun <T> apiHandler(call: suspend () -> T): Result<T> {
    return runCatching {
        Result.success(call())
    }.getOrElse { exception ->
        when (exception) {
            is ClientRequestException -> {
                Result.failure(Exception("Client Error: ${exception.message}"))
            }

            is ServerResponseException -> {
                Result.failure(Exception("Server Error: ${exception.message}"))
            }

            is ConnectException -> {
                Result.failure(Exception("Internet Connection!"))
            }

            else -> {
                Result.failure(Exception("Unknown Error: ${exception.message}"))
            }
        }
    }
}