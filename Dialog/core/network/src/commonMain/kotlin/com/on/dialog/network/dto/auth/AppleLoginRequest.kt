package com.on.dialog.network.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AppleLoginRequest(
    @SerialName("identityToken")
    val identityToken: String,
    @SerialName("firstName")
    val firstName: String?,
    @SerialName("lastName")
    val lastName: String?,
)
