package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val place: String,
    val maxParticipantCount: Int,
    val participantCount: Int,
) : DiscussionCatalog
