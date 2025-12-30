package com.on.dialog.data.service

import com.on.dialog.domain.Discussion
import com.on.dialog.data.dto.query.DiscussionQuery
import com.on.dialog.data.dto.response.DiscussionCreateResponse

interface DiscussionService {
    suspend fun getDiscussion(id: Long): Result<Discussion>

    suspend fun getDiscussions(): Result<List<Discussion>>

    suspend fun getMyDiscussions(): Result<List<Discussion>>

    suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<List<Discussion>>

    suspend fun deleteDiscussion(id: Long): Result<DiscussionCreateResponse>
}
