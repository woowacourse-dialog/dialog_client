package com.on.network.common

import com.on.network.dto.response.common.DataResponse
import de.jensklingenberg.ktorfit.Response
import kotlin.coroutines.cancellation.CancellationException

@Suppress("UNCHECKED_CAST")
internal suspend inline fun <T> safeApiCall(
    apiCall: suspend () -> Response<DataResponse<T>>
): Result<T> {
    return try {
        val response: Response<DataResponse<T>> = apiCall()

        if (!response.isSuccessful) {
            return Result.failure(
                HttpException(response)
            )
        }

        /**
        현재 우리 서비스에는 204가 내려오지 않으므로 Response body가 null로 오는 경우는 없음
         **/
        val body: DataResponse<T> = response.body()
            ?: return Result.failure(
                IllegalStateException("Response body가 null값입니다.")
            )

        val data = body.data
            ?: return Result.success(Unit as T)

        Result.success(data)
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}
