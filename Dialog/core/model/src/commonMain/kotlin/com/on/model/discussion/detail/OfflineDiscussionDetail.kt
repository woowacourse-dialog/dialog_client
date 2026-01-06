package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.offline.Participant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName

data class OfflineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val currentParticipantCount: Int,
    val maxParticipantCount: Int,
    val place: String,
    val participants: List<Participant>,
) : DiscussionDetail
