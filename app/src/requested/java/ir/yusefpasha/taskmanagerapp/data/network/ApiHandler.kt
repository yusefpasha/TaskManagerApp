package ir.yusefpasha.taskmanagerapp.data.network

import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiHandler(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = errorBody.orEmpty()
        Result.failure(Exception("${Constants.NETWORK_SERVER_ERROR_MESSAGE} -> ${e.code()} $errorResponse"))
    } catch (_: SocketTimeoutException) {
        Result.failure(Exception(Constants.NETWORK_TIMEOUT_ERROR_MESSAGE))
    } catch (_: IOException) {
        Result.failure(Exception(Constants.NETWORK_INTERNET_CONNECTION_ERROR_MESSAGE))
    } catch (e: Exception) {
        Result.failure(Exception("${Constants.NETWORK_UNKNOWN_MESSAGE} -> ${e.message}"))
    }
}