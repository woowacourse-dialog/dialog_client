package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
internal sealed interface CommentType {
    @Serializable
    data object Comment : CommentType

    @Serializable
    data class Reply(
        val commentId: Long,
    ) : CommentType

    @Serializable
    data class Edit(
        val commentId: Long,
        val originalContent: String,
    ) : CommentType
}
