package com.on.dialog.model.discussion.catalog

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionCatalog(
    override val catalogContent: com.on.dialog.model.discussion.content.CatalogContent,
    val endDate: com.on.dialog.model.discussion.datetimeperiod.EndDate,
) : com.on.dialog.model.discussion.catalog.DiscussionCatalog {
    override fun status(now: LocalDateTime): com.on.dialog.model.discussion.content.DiscussionStatus =
        when {
            endDate.isInPeriod(
                catalogContent.createdAt,
                now,
            ) -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.INDISCUSSION

            endDate.isAfterEnd(now) -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.DISCUSSIONCOMPLETE

            else -> _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.UNDEFINED
        }
}
