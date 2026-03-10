package com.on.dialog.network.datasource

import com.on.dialog.network.dto.comment.FetchDiscussionCommentsResponse
import com.on.dialog.network.dto.comment.PatchCommentRequest
import com.on.dialog.network.dto.comment.PostCommentRequest

interface CommentDatasource {
    suspend fun fetchComments(discussionId: Long): Result<FetchDiscussionCommentsResponse>

    suspend fun postComment(request: PostCommentRequest): Result<Unit>

    suspend fun patchComment(
        discussionCommentId: Long,
        request: PatchCommentRequest,
    ): Result<Unit>

    suspend fun deleteComment(discussionCommentId: Long): Result<Unit>
}
