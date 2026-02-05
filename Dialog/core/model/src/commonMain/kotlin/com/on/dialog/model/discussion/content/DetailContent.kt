package com.on.dialog.model.discussion.content

import com.on.dialog.model.common.Track
import kotlinx.datetime.LocalDateTime

data class DetailContent(
    val id: Long,
    val title: String,
    val author: Author,
    val category: Track,
    val content: String,
    val createdAt: LocalDateTime,
    val likeCount: Int,
    val modifiedAt: LocalDateTime,
)
