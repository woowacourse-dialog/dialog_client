package com.on.model

import kotlinx.datetime.LocalDateTime

data class Content(
    val id: Int,
    val title: String,
    val description: String,
    val category: DiscussionCategory,
    val author: Author,
    val status: DiscussionStatus,
    val discussionType: DiscussionType,
    val interactionCount: InteractionCount,
    val profileImage: ProfileImage,
    val createdAt: LocalDateTime,
)
