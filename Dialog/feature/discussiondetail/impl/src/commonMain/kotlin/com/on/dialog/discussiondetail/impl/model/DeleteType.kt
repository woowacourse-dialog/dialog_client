package com.on.dialog.discussiondetail.impl.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DeleteType {
    @Serializable
    data object Discussion : DeleteType

    @Serializable
    data class Comment(
        val commentId: Long,
    ) : DeleteType
}
