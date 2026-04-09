package com.on.dialog.network.service

import com.on.dialog.network.dto.comment.FetchDiscussionCommentsResponse
import com.on.dialog.network.dto.comment.PatchCommentRequest
import com.on.dialog.network.dto.comment.PostCommentRequest
import com.on.dialog.network.dto.common.DataResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

internal interface CommentService {
    @GET("api/discussions/{discussionId}/comments")
    suspend fun fetchComments(
        @Path("discussionId") discussionId: Long,
    ): DataResponse<FetchDiscussionCommentsResponse>

    @POST("api/discussions/comments")
    suspend fun postComment(
        @Body request: PostCommentRequest,
    ): DataResponse<Unit>

    @PATCH("api/discussions/comments/{discussionCommentId}")
    suspend fun patchComment(
        @Body request: PatchCommentRequest,
        @Path("discussionCommentId") discussionCommentId: Long,
    ): DataResponse<Unit>

    @DELETE("api/discussions/comments/{discussionCommentId}")
    suspend fun deleteComment(
        @Path("discussionCommentId") discussionCommentId: Long,
    ): DataResponse<Unit>
}
