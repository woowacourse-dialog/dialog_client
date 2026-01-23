package com.on.dialog.model.discussion.detail

import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

sealed interface DiscussionDetail {
    val detailContent: com.on.dialog.model.discussion.content.DetailContent
    val summary: String?

    fun status(now: LocalDateTime): com.on.dialog.model.discussion.content.DiscussionStatus
}
