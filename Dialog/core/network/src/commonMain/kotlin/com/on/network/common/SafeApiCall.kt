package com.on.network.common

import com.on.dialog.core.common.error.NetworkError
import com.on.network.dto.common.DataResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@Suppress("UNCHECKED_CAST")
internal suspend inline fun <T> safeApiCall(
    apiCall: suspend () -> DataResponse<T>,
): Result<T> {
    return try {
        val body: DataResponse<T> = apiCall()

        val data = body.data
            ?: return Result.success(Unit as T)

        Result.success(data)
    } catch (error: Exception) {
        if (error is CancellationException) throw error

        val networkError: NetworkError = when (error) {
            is ClientRequestException -> NetworkError.BadRequest(error)
            is ServerResponseException -> NetworkError.ServerError(error)
            is IOException -> NetworkError.Network(error)
            else -> NetworkError.Unknown(error)
        }

        Result.failure(networkError)
    }
}
