package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.CommentDatasource
import com.on.dialog.network.dto.comment.FetchDiscussionCommentsResponse
import com.on.dialog.network.dto.comment.PostCommentRequest
import com.on.dialog.network.service.CommentService

internal class CommentRemoteDatasource(
    val service: CommentService,
) : CommentDatasource {
    override suspend fun fetchComments(discussionId: Long): Result<FetchDiscussionCommentsResponse> =
        safeApiCall { service.fetchComments(discussionId) }

    override suspend fun postComment(request: PostCommentRequest): Result<Unit> =
        safeApiCall { service.postComment(request) }
}
