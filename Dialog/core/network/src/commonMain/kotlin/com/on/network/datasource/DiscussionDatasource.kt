package com.on.network.datasource

import com.on.network.dto.discussionlookup.DiscussionQuery
import com.on.network.dto.discussioncreate.CreateOfflineDiscussionRequest
import com.on.network.dto.discussioncreate.CreateOnlineDiscussionRequest
import com.on.network.dto.discussionsummary.DiscussionSummaryRequest
import com.on.network.dto.discussionedit.OfflineDiscussionEditRequest
import com.on.network.dto.discussionedit.OnlineDiscussionEditRequest
import com.on.network.dto.discussioncreate.DiscussionCreateResponse
import com.on.network.dto.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.discussionlookup.DiscussionCursorPageResponse
import com.on.network.dto.discussionsummary.DiscussionSummaryResponse

interface DiscussionDatasource {
    suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse>

    suspend fun getDiscussions(query: DiscussionQuery): Result<DiscussionCursorPageResponse>

    suspend fun getMyDiscussions(): Result<DiscussionCursorPageResponse>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DiscussionCursorPageResponse>

    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse>

    suspend fun createDiscussionSummary(
        request: DiscussionSummaryRequest,
    ): Result<DiscussionSummaryResponse>

    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
