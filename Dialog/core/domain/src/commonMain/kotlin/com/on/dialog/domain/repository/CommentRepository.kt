package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.comment.DiscussionComment

interface CommentRepository {
    suspend fun fetchComments(discussionId: Long): Result<List<DiscussionComment>>
}
