package com.on.dialog.data.repository

import com.on.dialog.domain.repository.CommentRepository
import com.on.dialog.model.discussion.comment.DiscussionComment
import com.on.dialog.network.datasource.CommentDatasource

class DefaultCommentRepository(
    val source: CommentDatasource,
) : CommentRepository {
    override suspend fun fetchComments(discussionId: Long): Result<List<DiscussionComment>> =
        runCatching {
            source.fetchComments(discussionId).getOrThrow()
        }.mapCatching { response ->
            response.toDomain()
        }
}
