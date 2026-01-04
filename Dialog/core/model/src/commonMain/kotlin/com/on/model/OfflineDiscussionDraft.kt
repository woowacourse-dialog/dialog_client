package com.on.model

data class OfflineDiscussionDraft(
    val title: String,
    val content: String,
    val startAt: String,
    val endAt: String,
    val place: String,
    val maxParticipantCount: Int,
    val category: String,
)
