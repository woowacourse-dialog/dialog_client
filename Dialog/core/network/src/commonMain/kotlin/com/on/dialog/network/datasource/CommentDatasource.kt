package com.on.dialog.network.datasource

import com.on.dialog.network.dto.comment.FetchDiscussionCommentsResponse

interface CommentDatasource {
    suspend fun fetchComments(discussionId: Long): Result<FetchDiscussionCommentsResponse>
}
