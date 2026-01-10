package com.on.dialog.core.common.error

sealed class NetworkError(
    cause: Exception,
) : Exception(cause) {
    class BadRequest(
        cause: Exception,
    ) : NetworkError(cause)

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
