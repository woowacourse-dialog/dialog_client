package com.on.dialog.discussiondetail.impl.model

internal sealed interface DeleteType {
    data object Discussion : DeleteType

    data class Comment(
        val commentId: Long,
    ) : DeleteType
}
