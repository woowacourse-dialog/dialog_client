package com.on.dialog.domain.repository

import com.on.model.DiscussionSummary

interface DiscussionSummaryRepository {
    suspend fun createDiscussionSummary(
        discussionId: Long,
    ): Result<DiscussionSummary>
}