package com.on.dialog.network.datasource

import com.on.dialog.network.dto.discussioncreate.CreateOfflineDiscussionRequest
import com.on.dialog.network.dto.discussioncreate.CreateOnlineDiscussionRequest
import com.on.dialog.network.dto.discussioncreate.OfflineDiscussionCreateResponse
import com.on.dialog.network.dto.discussioncreate.OnlineDiscussionCreateResponse
import com.on.dialog.network.dto.discussiondetail.DiscussionDetailResponse
import com.on.dialog.network.dto.discussionedit.OfflineDiscussionEditRequest
import com.on.dialog.network.dto.discussionedit.OnlineDiscussionEditRequest
import com.on.dialog.network.dto.discussionlookup.DiscussionCursorPageResponse
import com.on.dialog.network.dto.discussionlookup.DiscussionQuery
import com.on.dialog.network.dto.discussionsummary.DiscussionSummaryRequest
import com.on.dialog.network.dto.discussionsummary.DiscussionSummaryResponse

interface DiscussionDatasource {
    suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse>

    suspend fun getDiscussions(
        query: DiscussionQuery,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCursorPageResponse>

    suspend fun getMyDiscussions(cursor: String?, size: Int): Result<DiscussionCursorPageResponse>

    suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        query: DiscussionQuery,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCursorPageResponse>

    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<OfflineDiscussionCreateResponse>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<OnlineDiscussionCreateResponse>

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
