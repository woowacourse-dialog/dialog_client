package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val endDate: LocalDateTime,
) : DiscussionCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now in catalogContent.createdAt..endDate -> DiscussionStatus.INDISCUSSION
            now > endDate -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
