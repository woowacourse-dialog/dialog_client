package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.comment.DiscussionComment

interface CommentRepository {
    suspend fun fetchComments(discussionId: Long): Result<List<DiscussionComment>>

    suspend fun postComment(discussionId: Long, content: String): Result<Unit>

    suspend fun postReply(discussionId: Long, parentCommentId: Long, content: String): Result<Unit>

    suspend fun patchComment(discussionCommentId: Long, content: String): Result<Unit>

    suspend fun deleteComment(discussionCommentId: Long): Result<Unit>
}
