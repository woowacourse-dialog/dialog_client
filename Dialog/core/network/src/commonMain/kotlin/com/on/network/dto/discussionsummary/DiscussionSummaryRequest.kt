package com.on.network.dto.discussionsummary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionSummaryRequest(
    @SerialName("discussionId")
    val discussionId: Long,
) {
    companion object {
        fun Long.toResponse(): DiscussionSummaryRequest =
            DiscussionSummaryRequest(
                discussionId = this,
            )
    }
}
