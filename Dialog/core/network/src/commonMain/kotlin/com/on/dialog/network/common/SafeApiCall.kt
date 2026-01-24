package com.on.dialog.network.common

import com.on.dialog.core.common.error.NetworkError
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
            is ClientRequestException -> {
                // 에러 응답 본문을 파싱하여 errorCode 확인
                val errorResponse = runCatching { error.response.body<ErrorResponse>() }.getOrNull()

                // errorCode가 1005인 경우 (로그인 필요) Unauthorized 에러로 처리
                if (errorResponse?.errorCode == "1005") {
                    NetworkError.Unauthorized(
                        cause = error,
                        errorCode = errorResponse.errorCode,
                        errorMessage = errorResponse.message,
                    )
                } else {
                    NetworkError.BadRequest(error)
                }
            }

            is ServerResponseException -> {
                NetworkError.ServerError(error)
            }

            is IOException -> {
                NetworkError.Network(error)
            }

            else -> {
                NetworkError.Unknown(error)
            }
        }
        Result.failure(networkError)
    }
}
