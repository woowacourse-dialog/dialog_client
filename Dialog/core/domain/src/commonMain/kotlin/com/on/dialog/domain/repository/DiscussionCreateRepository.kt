package com.on.dialog.domain.repository

import com.on.model.discussion.draft.OfflineDiscussionDraft
import com.on.model.discussion.draft.OnlineDiscussionDraft

interface DiscussionCreateRepository {
    suspend fun createOfflineDiscussion(request: OfflineDiscussionDraft): Result<Unit>

    suspend fun createOnlineDiscussion(request: OnlineDiscussionDraft): Result<Unit>
}
