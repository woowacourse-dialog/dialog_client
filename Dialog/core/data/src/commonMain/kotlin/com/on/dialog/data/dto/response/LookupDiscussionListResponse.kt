package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LookupDiscussionListResponse(
    @SerialName("data")
    val data: DiscussionCursorPageResponse,
)
