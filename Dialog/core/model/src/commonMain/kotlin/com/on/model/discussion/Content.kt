package com.on.model.discussion

import com.on.model.discussion.content.Author
import com.on.model.discussion.content.DiscussionCategory
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.content.DiscussionType
import com.on.model.discussion.content.InteractionCount
import com.on.model.discussion.content.ProfileImage
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