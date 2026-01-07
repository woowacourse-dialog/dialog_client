package com.on.model.discussion.catalog

import com.on.dialog.core.common.extension.atEndOfDay
import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val endDate: LocalDate,
) : DiscussionCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now in catalogContent.createdAt..endDate.atEndOfDay() -> DiscussionStatus.INDISCUSSION
            now > endDate.atEndOfDay() -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
