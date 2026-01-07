package com.on.model.discussion.detail

import com.on.dialog.core.common.extension.atEndOfDay
import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val endDate: LocalDate,
) : DiscussionDetail {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            now in detailContent.createdAt..endDate.atEndOfDay() -> DiscussionStatus.INDISCUSSION
            now > endDate.atEndOfDay() -> DiscussionStatus.DISCUSSIONCOMPLETE
            else -> DiscussionStatus.UNDEFINED
        }
}
