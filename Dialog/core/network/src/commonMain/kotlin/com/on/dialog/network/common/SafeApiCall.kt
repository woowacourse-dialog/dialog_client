package com.on.dialog.network.common

import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.model.error.DialogError
import com.on.dialog.model.error.DialogErrorHttpStatus
import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.common.ErrorResponse
import io.ktor.client.call.body
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
            is ClientRequestException -> handleClientRequestException(error)
            is ServerResponseException -> NetworkError.ServerError(error)
            is IOException -> NetworkError.Network(error)
            else -> NetworkError.Unknown(error)
        }
        Result.failure(networkError)
    }
}

private suspend fun handleClientRequestException(error: ClientRequestException): NetworkError {
    // 에러 응답 본문을 파싱하여 errorCode 확인
    val errorResponse = runCatching { error.response.body<ErrorResponse>() }.getOrNull()
    val errorCode: String = errorResponse?.errorCode ?: return NetworkError.Unknown(error)

    val dialogError: DialogError =
        DialogError.fromCode(errorCode) ?: return NetworkError.Unknown(error)

    return when (dialogError.httpStatus) {
        DialogErrorHttpStatus.UNAUTHORIZED -> NetworkError.Unauthorized(
            cause = error,
            errorCode = dialogError.code,
            errorMessage = dialogError.message,
        )

        DialogErrorHttpStatus.NOT_FOUND -> NetworkError.NotFound(
            cause = error,
            errorCode = dialogError.code,
            errorMessage = dialogError.message,
        )

        DialogErrorHttpStatus.BAD_REQUEST -> NetworkError.BadRequest(
            cause = error,
            errorCode = dialogError.code,
            errorMessage = dialogError.message,
        )
    }
}
