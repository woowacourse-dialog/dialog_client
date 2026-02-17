package com.on.dialog.core.common.error

sealed class NetworkError(
    cause: Exception,
) : Exception(cause) {
    class BadRequest(
        cause: Exception,
    ) : NetworkError(cause)

    class Unauthorized(
        cause: Exception,
        val errorCode: String,
        val errorMessage: String,
    ) : NetworkError(cause)

    class ServerError(
        cause: Exception,
    ) : NetworkError(cause)

    class ServerCustomError(
        cause: Exception,
        val errorCode: String,
    ) : NetworkError(cause)

    class Network(
        cause: Exception,
    ) : NetworkError(cause)

    class Unknown(
        cause: Exception,
    ) : NetworkError(cause)
}
