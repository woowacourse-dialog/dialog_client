package com.on.dialog.model.discussion.comment

import com.on.dialog.model.discussion.content.Author
import kotlinx.datetime.LocalDateTime

data class DiscussionComment(
    val id: Long,
    val content: String,
    val author: Author,
    val childComments: List<DiscussionComment>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
