package ir.yusefpasha.taskmanagerapp.data.network

import kotlinx.io.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

suspend fun <T> apiHandler(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = errorBody.orEmpty()
        Result.failure(Exception("Server Error: ${e.code()} - $errorResponse"))
    } catch (e: SocketTimeoutException) {
        Result.failure(Exception("Timeout!"))
    } catch (e: IOException) {
        Result.failure(Exception("Internet Connection!"))
    } catch (e: Exception) {
        Result.failure(Exception("Unknown Error: ${e.message}"))
    }
}