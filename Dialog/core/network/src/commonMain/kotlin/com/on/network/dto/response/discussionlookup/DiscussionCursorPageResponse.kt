package com.on.network.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCursorPageResponse(
    @SerialName("content")
    val content: List<Content?>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: String,
)
