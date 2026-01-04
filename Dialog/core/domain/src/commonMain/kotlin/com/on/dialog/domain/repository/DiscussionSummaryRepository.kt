package com.on.dialog.domain.repository

import com.on.model.discussion.summary.DiscussionSummary

interface DiscussionSummaryRepository {
    suspend fun createDiscussionSummary(
        discussionId: Long,
    ): Result<DiscussionSummary>
}
