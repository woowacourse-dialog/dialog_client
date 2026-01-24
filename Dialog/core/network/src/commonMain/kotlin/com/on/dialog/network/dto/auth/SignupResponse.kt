package com.on.dialog.network.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SignupResponse(
    @SerialName("userId")
    val userId: Long,
)
