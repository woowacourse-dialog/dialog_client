package com.on.dialog.network.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SignupRequest(
    @SerialName("track")
    val track: String,
    @SerialName("webPushNotification")
    val webPushNotification: Boolean,
)
