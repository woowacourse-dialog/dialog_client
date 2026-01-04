package com.on.dialog.domain.repository

import com.on.model.discussion.draft.OfflineDiscussionDraft
import com.on.model.discussion.draft.OnlineDiscussionDraft

interface DiscussionUpdateRepository {
    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionDraft,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionDraft,
    ): Result<Unit>
}