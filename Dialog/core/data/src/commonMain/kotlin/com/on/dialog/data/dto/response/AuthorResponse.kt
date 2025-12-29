package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("track")
    val track: String,
)
