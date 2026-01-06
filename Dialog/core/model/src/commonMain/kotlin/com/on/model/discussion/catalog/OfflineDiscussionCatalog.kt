package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import kotlinx.serialization.SerialName

data class OfflineDiscussionCatalog (
    override val catalogContent: CatalogContent,
    override val summary: String?,
    val startAt: String,
    val endAt: String,
    val place: String,
    val maxParticipantCount: Int,
    val participantCount: Int,
) : DiscussionCatalog