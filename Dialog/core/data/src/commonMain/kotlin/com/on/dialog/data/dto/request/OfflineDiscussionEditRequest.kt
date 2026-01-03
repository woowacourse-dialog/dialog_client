package com.on.dialog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionEditRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("place")
    val place: String,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerialName("category")
    val category: String,
)
