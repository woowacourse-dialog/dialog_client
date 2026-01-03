package com.on.dialog.data.dto.response.discussioncatalog

import com.on.dialog.data.dto.response.discussioncatalog.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCatalogResponse(
    @SerialName("content")
    val content: List<Content>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: String,
)
