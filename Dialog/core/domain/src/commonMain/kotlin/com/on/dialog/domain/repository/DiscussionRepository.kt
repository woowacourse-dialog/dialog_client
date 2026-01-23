package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.criteria.DiscussionCriteria
import com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import com.on.dialog.model.discussion.draft.OnlineDiscussionDraft
import com.on.dialog.model.discussion.summary.DiscussionSummary

interface DiscussionRepository {
    suspend fun getDiscussionDetail(id: Long): Result<com.on.dialog.model.discussion.detail.DiscussionDetail>

    suspend fun getDiscussions(
        discussionCriteria: com.on.dialog.model.discussion.criteria.DiscussionCriteria,
        cursor: String?,
        size: Int,
    ): Result<com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage>

    suspend fun getMyDiscussions(
        cursor: String?,
        size: Int,
    ): Result<com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        discussionCriteria: com.on.dialog.model.discussion.criteria.DiscussionCriteria,
        cursor: String?,
        size: Int,
    ): Result<com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage>

    suspend fun createOfflineDiscussion(request: com.on.dialog.model.discussion.draft.OfflineDiscussionDraft): Result<Long>

    suspend fun createOnlineDiscussion(request: com.on.dialog.model.discussion.draft.OnlineDiscussionDraft): Result<Long>

    suspend fun createDiscussionSummary(
        discussionId: Long,
    ): Result<com.on.dialog.model.discussion.summary.DiscussionSummary>

    suspend fun updateOfflineDiscussion(
        id: Long,
        offlineDiscussionDraft: com.on.dialog.model.discussion.draft.OfflineDiscussionDraft,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        onlineDiscussionDraft: com.on.dialog.model.discussion.draft.OnlineDiscussionDraft,
    ): Result<Unit>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
