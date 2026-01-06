package com.on.dialog.domain.repository

import com.on.model.discussion.criteria.DiscussionCriteria
import com.on.model.discussion.cursorpage.DiscussionCatalogCursorPage
import com.on.model.discussion.detail.DiscussionDetail
import com.on.model.discussion.draft.OfflineDiscussionDraft
import com.on.model.discussion.draft.OnlineDiscussionDraft
import com.on.model.discussion.summary.DiscussionSummary

interface DiscussionRepository {
    suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetail>

    suspend fun getDiscussions(
        discussionCriteria: DiscussionCriteria,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCatalogCursorPage>

    suspend fun getMyDiscussions(): Result<DiscussionCatalogCursorPage>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        discussionCriteria: DiscussionCriteria,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCatalogCursorPage>

    suspend fun createOfflineDiscussion(request: OfflineDiscussionDraft): Result<Long>

    suspend fun createOnlineDiscussion(request: OnlineDiscussionDraft): Result<Long>

    suspend fun createDiscussionSummary(
        discussionId: Long,
    ): Result<DiscussionSummary>

    suspend fun updateOfflineDiscussion(
        id: Long,
        offlineDiscussionDraft: OfflineDiscussionDraft,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        onlineDiscussionDraft: OnlineDiscussionDraft,
    ): Result<Unit>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
