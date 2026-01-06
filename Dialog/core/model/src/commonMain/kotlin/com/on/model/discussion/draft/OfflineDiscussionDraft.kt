package com.on.model.discussion.draft

import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDraft(
    val title: String,
    val content: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val place: String,
    val maxParticipantCount: Int,
    val category: String,
)
