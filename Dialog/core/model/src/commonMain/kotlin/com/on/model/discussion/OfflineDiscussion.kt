package com.on.model.discussion

import com.on.model.discussion.offline.Participant
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussion(
    override val content: Content,
    override val summary: String?,
    val currentParticipantCount: Int,
    val maxParticipantCount: Int,
    val place: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val participants: List<Participant>,
) : Discussion
