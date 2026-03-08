package com.on.dialog.model.discussion.detail

import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val endDate: EndDate,
) : DiscussionDetail {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now < detailContent.createdAt -> DiscussionStatus.RECRUITING

            endDate.isInPeriod(
                startAt = detailContent.createdAt,
                dateTime = now,
            ) -> DiscussionStatus.IN_DISCUSSION

            endDate.isAfterEnd(dateTime = now) -> DiscussionStatus.DISCUSSION_COMPLETE

            else -> DiscussionStatus.DISCUSSION_COMPLETE
        }
}
