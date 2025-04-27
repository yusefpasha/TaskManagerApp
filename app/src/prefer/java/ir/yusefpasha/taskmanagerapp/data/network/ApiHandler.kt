package ir.yusefpasha.taskmanagerapp.data.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import java.net.ConnectException

suspend fun <T> apiHandler(call: suspend () -> T): Result<T> {
    return runCatching {
        Result.success(call())
    }.getOrElse { exception ->
        when (exception) {
            is ClientRequestException -> {
                Result.failure(Exception("${Constants.NETWORK_CLIENT_ERROR_MESSAGE} -> ${exception.message}"))
            }

            is ServerResponseException -> {
                Result.failure(Exception("${Constants.NETWORK_SERVER_ERROR_MESSAGE} -> ${exception.message}"))
            }

            is ConnectException -> {
                Result.failure(Exception(Constants.NETWORK_INTERNET_CONNECTION_ERROR_MESSAGE))
            }

            else -> {
                Result.failure(Exception("${Constants.NETWORK_UNKNOWN_MESSAGE} -> ${exception.message}"))
            }
        }
    }
}