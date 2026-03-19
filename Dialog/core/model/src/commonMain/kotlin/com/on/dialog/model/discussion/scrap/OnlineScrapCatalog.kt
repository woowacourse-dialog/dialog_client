package com.on.dialog.model.discussion.scrap

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineScrapCatalog(
    override val catalogContent: CatalogContent,
    val endDate: EndDate,
) : ScrapCatalog {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            endDate.isInPeriod(
                startAt = catalogContent.createdAt,
                dateTime = now,
            ) -> DiscussionStatus.IN_DISCUSSION

            endDate.isAfterEnd(dateTime = now) -> DiscussionStatus.DISCUSSION_COMPLETE

            else -> throw IllegalStateException("Unknown DiscussionStatus")
        }
}
