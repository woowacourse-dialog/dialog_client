package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.offline.Participant
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val currentParticipantCount: Int,
    val maxParticipantCount: Int,
    val place: String,
    val participants: List<Participant>,
) : DiscussionDetail {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now < startAt -> DiscussionStatus.RECRUITING
            now < startAt && currentParticipantCount >= maxParticipantCount ->
                DiscussionStatus.RECRUITCOMPLETE
            now in startAt..endAt -> DiscussionStatus.INDISCUSSION
            now > endAt -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
