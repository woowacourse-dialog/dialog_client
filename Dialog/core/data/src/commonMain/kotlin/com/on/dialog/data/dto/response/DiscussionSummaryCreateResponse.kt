package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionSummaryCreateResponse(
    @SerialName("discussionId")
    val discussionId: Int,
    @SerialName("summary")
    val summary: String,
)
