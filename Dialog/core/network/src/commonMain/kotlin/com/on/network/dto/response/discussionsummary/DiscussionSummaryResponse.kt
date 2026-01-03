package com.on.network.dto.response.discussionsummary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionSummaryResponse(
    @SerialName("discussionId")
    val discussionId: Int,
    @SerialName("summary")
    val summary: String,
)
