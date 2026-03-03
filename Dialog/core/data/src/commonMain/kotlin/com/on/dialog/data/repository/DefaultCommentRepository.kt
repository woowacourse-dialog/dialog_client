package com.on.dialog.data.repository

import com.on.dialog.domain.repository.CommentRepository
import com.on.dialog.model.discussion.comment.DiscussionComment
import com.on.dialog.network.datasource.CommentDatasource
import com.on.dialog.network.dto.comment.PostCommentRequest

class DefaultCommentRepository(
    val source: CommentDatasource,
) : CommentRepository {
    override suspend fun fetchComments(discussionId: Long): Result<List<DiscussionComment>> =
        source
            .fetchComments(discussionId)
            .mapCatching { response -> response.toDomain() }

    override suspend fun postComment(
        discussionId: Long,
        content: String,
    ): Result<Unit> =
        source.postComment(PostCommentRequest(discussionId = discussionId, content = content))

    override suspend fun postReply(
        discussionId: Long,
        parentCommentId: Long,
        content: String,
    ): Result<Unit> =
        source.postComment(
            PostCommentRequest(
                discussionId = discussionId,
                content = content,
                parentDiscussionCommentId = parentCommentId,
            ),
        )
}
