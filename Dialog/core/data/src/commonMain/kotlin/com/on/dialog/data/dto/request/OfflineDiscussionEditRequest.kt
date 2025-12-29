package com.on.dialog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionEditRequest(
    @SerialName("category")
    val category: String,
    @SerialName("content")
    val content: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerialName("place")
    val place: String,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("title")
    val title: String,
)
