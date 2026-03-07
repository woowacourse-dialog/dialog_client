package com.on.dialog.network.dto.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchCommentRequest(
    @SerialName("content")
    val content: String,
)
