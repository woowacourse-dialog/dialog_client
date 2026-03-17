package com.on.dialog.network.dto.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyTokenResponse(
    @SerialName("token")
    val token: String,
)
