package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCursorPageResponse(
    @SerialName("content")
    val discussionPreviewResponse: List<DiscussionPreviewResponse>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: String,
)
