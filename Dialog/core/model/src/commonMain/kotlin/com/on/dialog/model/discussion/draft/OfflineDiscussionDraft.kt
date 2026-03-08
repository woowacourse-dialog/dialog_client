package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDraft(
    override val title: String,
    override val content: String,
    override val category: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val place: String,
    val maxParticipantCount: Int,
) : DiscussionDraft
