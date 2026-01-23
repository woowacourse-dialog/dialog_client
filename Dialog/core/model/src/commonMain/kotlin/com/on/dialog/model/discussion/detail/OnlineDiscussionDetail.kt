package com.on.dialog.model.discussion.detail

import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionDetail(
    override val detailContent: com.on.dialog.model.discussion.content.DetailContent,
    override val summary: String?,
    val endDate: com.on.dialog.model.discussion.datetimeperiod.EndDate,
) : com.on.dialog.model.discussion.detail.DiscussionDetail {
    override fun status(now: LocalDateTime): com.on.dialog.model.discussion.content.DiscussionStatus =
        when {
            endDate.isInPeriod(
                detailContent.createdAt,
                now
            ) -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.INDISCUSSION

            endDate.isAfterEnd(now) -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.DISCUSSIONCOMPLETE
            else -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.UNDEFINED
        }
}
