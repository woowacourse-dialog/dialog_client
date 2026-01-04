package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionDatasource
import com.on.network.dto.query.DiscussionQuery
import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.request.OfflineDiscussionEditRequest
import com.on.network.dto.request.OnlineDiscussionEditRequest
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse
import com.on.network.service.DiscussionService

class DiscussionRemoteDatasource(
    private val discussionService: DiscussionService,
) : DiscussionDatasource {
    override suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse> =
        safeApiCall { discussionService.getDiscussionDetail(id) }

    override suspend fun getDiscussions(query: DiscussionQuery): Result<DiscussionCursorPageResponse> =
        safeApiCall { discussionService.getDiscussions(query.toQueryMap()) }

    override suspend fun getMyDiscussions(): Result<DiscussionCursorPageResponse> =
        safeApiCall { discussionService.getMyDiscussions() }

    override suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DiscussionCursorPageResponse> =
        safeApiCall { discussionService.searchDiscussions(searchBy, keyword, query.toQueryMap()) }

    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse> =
        safeApiCall { discussionService.postOfflineDiscussion(request) }

    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse> =
        safeApiCall { discussionService.postOnlineDiscussion(request) }

    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> =
        safeApiCall { discussionService.postDiscussionSummary(request) }

    override suspend fun deleteDiscussion(id: Long): Result<Unit> =
        safeApiCall { discussionService.deleteDiscussion(id) }

    override suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOfflineDiscussion(id, request) }

    override suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOnlineDiscussion(id, request) }
}
