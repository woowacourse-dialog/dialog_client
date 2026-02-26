package com.on.dialog.core.common.error

sealed class NetworkError(
    cause: Exception,
    val errorCode: String? = null,
    val errorMessage: String? = null,
) : Exception(cause) {
    class BadRequest(
        cause: Exception,
        errorCode: String,
        errorMessage: String,
    ) : NetworkError(cause, errorCode, errorMessage)

    class Unauthorized(
        cause: Exception,
        errorCode: String,
        errorMessage: String,
    ) : NetworkError(cause, errorCode, errorMessage)

    class NotFound(
        cause: Exception,
        errorCode: String,
        errorMessage: String,
    ) : NetworkError(cause, errorCode, errorMessage)

    class ServerError(
        cause: Exception,
    ) : NetworkError(cause)

    class Network(
        cause: Exception,
    ) : NetworkError(cause)

    class Unknown(
        cause: Exception,
    ) : NetworkError(cause)
}
