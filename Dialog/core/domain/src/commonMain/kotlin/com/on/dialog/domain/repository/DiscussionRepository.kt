package com.on.dialog.domain.repository

import com.on.model.discussion.detail.DiscussionDetail
import com.on.model.discussion.criteria.DiscussionCriteria
import com.on.model.discussion.draft.OfflineDiscussionDraft
import com.on.model.discussion.draft.OnlineDiscussionDraft
import com.on.model.discussion.summary.DiscussionSummary

interface DiscussionRepository {
    suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetail>

    suspend fun getDiscussions(
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<List<DiscussionDetail>>

    suspend fun getMyDiscussions(): Result<List<DiscussionDetail>>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<List<DiscussionDetail>>

    suspend fun createOfflineDiscussion(request: OfflineDiscussionDraft): Result<Unit>

    suspend fun createOnlineDiscussion(request: OnlineDiscussionDraft): Result<Unit>

    suspend fun createDiscussionSummary(
        discussionId: Long,
    ): Result<DiscussionSummary>

    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionDraft,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionDraft,
    ): Result<Unit>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
