package com.on.dialog.domain.repository

import com.on.model.discussion.Discussion
import com.on.model.discussion.criteria.DiscussionCriteria

interface DiscussionRepository {
    suspend fun getDiscussionDetail(id: Long): Result<Discussion>

    suspend fun getDiscussions(
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<List<Discussion>>

    suspend fun getMyDiscussions(): Result<List<Discussion>>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<List<Discussion>>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
