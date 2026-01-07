package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

sealed interface DiscussionDetail {
    val detailContent: DetailContent
    val summary: String?

    fun status(now: LocalDateTime): DiscussionStatus
}
