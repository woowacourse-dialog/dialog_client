package com.on.dialog.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserMypageUpdateRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("track")
    val track: String,
)
