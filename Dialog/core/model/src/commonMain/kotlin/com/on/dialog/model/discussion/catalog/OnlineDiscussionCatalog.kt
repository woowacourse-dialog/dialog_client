package com.on.dialog.model.discussion.catalog

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val endDate: EndDate,
) : DiscussionCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            endDate.isInPeriod(
                startAt = catalogContent.createdAt,
                dateTime = now,
            ) -> DiscussionStatus.INDISCUSSION

            endDate.isAfterEnd(dateTime = now) -> DiscussionStatus.DISCUSSIONCOMPLETE

            else -> DiscussionStatus.UNDEFINED
        }
}
