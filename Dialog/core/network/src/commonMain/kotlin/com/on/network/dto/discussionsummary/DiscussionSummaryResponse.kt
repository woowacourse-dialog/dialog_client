package com.on.network.dto.discussionsummary

import com.on.model.discussion.summary.DiscussionSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionSummaryResponse(
    @SerialName("discussionId")
    val discussionId: Long,
    @SerialName("summary")
    val summary: String,
) {
    fun toDomain(): DiscussionSummary =
        DiscussionSummary(
            discussionId = discussionId,
            summary = summary
        )
}
