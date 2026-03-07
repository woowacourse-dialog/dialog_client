package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.formatToString
import com.on.dialog.model.discussion.comment.DiscussionComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime

@Immutable
internal data class DiscussionCommentUiModel(
    private val createdAt: LocalDateTime,
    val commentId: Long,
    val content: String,
    val authorName: String,
    val authorAvatar: String?,
    val isMine: Boolean,
    val childComments: ImmutableList<DiscussionCommentUiModel> = persistentListOf(),
) {
    val formattedCreatedAt: String = createdAt.formatToString("yyyy년 M월 d일 HH:mm")

    companion object {
        fun DiscussionComment.toUiModel(userId: Long): DiscussionCommentUiModel =
            DiscussionCommentUiModel(
                createdAt = createdAt,
                commentId = id,
                content = content,
                authorName = author.nickname,
                authorAvatar = author.profileImage?.let { profileImage ->
                    profileImage.customImageUri ?: profileImage.basicImageUri
                },
                isMine = author.id == userId,
                childComments = childComments.map { child -> child.toUiModel(userId = userId) }.toImmutableList(),
            )
    }
}
