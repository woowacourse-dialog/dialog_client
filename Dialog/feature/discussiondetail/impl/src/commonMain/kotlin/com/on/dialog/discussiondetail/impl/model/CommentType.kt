package com.on.dialog.discussiondetail.impl.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface CommentType {
    data object Comment : CommentType

    data class Reply(
        val commentId: Long,
    ) : CommentType

    data class Edit(
        val commentId: Long,
        val originalContent: String,
    ) : CommentType
}
