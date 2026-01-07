package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val place: String,
    val maxParticipantCount: Int,
    val participantCount: Int,
) : DiscussionCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now < startAt -> DiscussionStatus.RECRUITING
            now < startAt && participantCount >= maxParticipantCount ->
                DiscussionStatus.RECRUITCOMPLETE
            now in startAt..endAt -> DiscussionStatus.INDISCUSSION
            now > endAt -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
