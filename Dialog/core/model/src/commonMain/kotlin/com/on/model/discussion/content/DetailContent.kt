package com.on.model.discussion.content

import kotlinx.datetime.LocalDateTime

data class DetailContent(
    val id: Int,
    val discussionType: String,
    val title: String,
    val author: Author,
    val category: DiscussionCategory,
    val content: String,
    val createdAt: LocalDateTime,
    val likeCount: Int,
    val modifiedAt: LocalDateTime,
    val summary: String,
)
