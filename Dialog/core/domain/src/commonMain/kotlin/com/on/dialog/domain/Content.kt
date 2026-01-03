package com.on.dialog.domain

data class Content(
    val id: Int,
    val title: String,
    val category: DiscussionCategory,
    val author: Author,
    val status: DiscussionStatus,
    val discussionType: DiscussionType,
    val interactionCount: InteractionCount,
    val profileImage: ProfileImage,
    val createdAt: String,
)
