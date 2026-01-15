package com.on.model.discussion.content

import com.on.model.common.ProfileImage
import kotlinx.datetime.LocalDateTime

data class CatalogContent(
    val id: Long,
    val discussionType: String,
    val title: String,
    val author: String,
    val category: DiscussionCategory,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val commentCount: Int,
    val profileImage: ProfileImage?,
)
