package com.on.dialog.domain

data class OfflineDiscussion(
    override val content: Content,
    override val summary: String?,
    override val endDate: String,
    val currentParticipantCount: Int,
    val maxParticipantCount: Int,
    val place: String,
    val startAt: String,
    val endAt: String,
) : Discussion
