package com.on.dialog.network.common

import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.model.error.DialogError
import com.on.dialog.model.error.DialogErrorHttpStatus
import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.common.ErrorResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
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
            is ServerResponseException -> handleServerResponseException(error)
            is IOException -> NetworkError.Network(error)
            else -> NetworkError.Unknown(error)
        }
        Result.failure(networkError)
    }
}

private suspend fun handleClientRequestException(error: ClientRequestException): NetworkError {
    val dialogError: DialogError = parseDialogError(error = error)
        ?: return NetworkError.Unknown(error)

    return dialogError.toNetworkError(cause = error)
}

private suspend fun handleServerResponseException(error: ServerResponseException): NetworkError {
    val dialogError: DialogError = parseDialogError(error = error)
        ?: return NetworkError.ServerError(error)

    return dialogError.toNetworkError(cause = error)
}

private suspend fun parseDialogError(error: ResponseException): DialogError? {
    val errorResponse: ErrorResponse =
        runCatching { error.response.body<ErrorResponse>() }
            .getOrNull()
            ?: return null
    return DialogError.fromCode(errorResponse.errorCode)
}

private fun DialogError.toNetworkError(cause: Exception): NetworkError = when (httpStatus) {
    DialogErrorHttpStatus.BAD_REQUEST -> NetworkError.BadRequest(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.UNAUTHORIZED -> NetworkError.Unauthorized(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.FORBIDDEN -> NetworkError.Forbidden(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.NOT_FOUND -> NetworkError.NotFound(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.CONFLICT -> NetworkError.Conflict(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.INTERNAL_SERVER_ERROR -> NetworkError.InternalServerError(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )

    DialogErrorHttpStatus.BAD_GATEWAY -> NetworkError.BadGateway(
        cause = cause,
        errorCode = code,
        errorMessage = message,
    )
}
