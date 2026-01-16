package com.on.network.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("errorCode")
    val errorCode: String,
    @SerialName("message")
    val message: String,
)
