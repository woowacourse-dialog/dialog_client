package com.on.dialog.domain.repository

import com.on.model.OfflineDiscussionDraft
import com.on.model.OnlineDiscussionDraft

interface DiscussionCreateRepository {
    suspend fun createOfflineDiscussion(request: OfflineDiscussionDraft): Result<Unit>

    suspend fun createOnlineDiscussion(request: OnlineDiscussionDraft): Result<Unit>
}