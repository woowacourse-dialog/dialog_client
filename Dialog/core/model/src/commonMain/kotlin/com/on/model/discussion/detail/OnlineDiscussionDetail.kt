package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val endDate: EndDate,
) : DiscussionDetail {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            endDate.isInPeriod(detailContent.createdAt,now) -> DiscussionStatus.INDISCUSSION
            endDate.isAfterEnd(now) -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
