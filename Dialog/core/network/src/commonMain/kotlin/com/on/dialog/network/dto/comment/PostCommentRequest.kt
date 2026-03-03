package com.on.dialog.network.dto.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCommentRequest(
    @SerialName("content")
    val content: String,
    @SerialName("discussionId")
    val discussionId: Long,
    @SerialName("parentDiscussionCommentId")
    val parentDiscussionCommentId: Long? = null,
)
