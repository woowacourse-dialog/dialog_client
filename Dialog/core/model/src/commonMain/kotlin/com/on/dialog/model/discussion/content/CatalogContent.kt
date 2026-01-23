package com.on.dialog.model.discussion.content

import com.on.dialog.model.common.ProfileImage
import kotlinx.datetime.LocalDateTime

data class CatalogContent(
    val id: Long,
    val title: String,
    val author: String,
    val category: com.on.dialog.model.discussion.content.DiscussionCategory,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val commentCount: Int,
    val profileImage: com.on.dialog.model.common.ProfileImage?,
)
