package com.on.model.discussion.catalog

import com.on.dialog.core.common.extension.atEndOfDay
import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val endDate: EndDate,
) : DiscussionCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            endDate.isInPeriod(catalogContent.createdAt,now) -> DiscussionStatus.INDISCUSSION
            endDate.isAfterEnd(now) -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
