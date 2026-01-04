package com.on.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionSummaryRequest(
    @SerialName("discussionId")
    val discussionId: Long,
)
