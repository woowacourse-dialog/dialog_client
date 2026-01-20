package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionDatasource
import com.on.network.dto.discussioncreate.CreateOfflineDiscussionRequest
import com.on.network.dto.discussioncreate.CreateOnlineDiscussionRequest
import com.on.network.dto.discussioncreate.OfflineDiscussionCreateResponse
import com.on.network.dto.discussioncreate.OnlineDiscussionCreateResponse
import com.on.network.dto.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.discussionedit.OfflineDiscussionEditRequest
import com.on.network.dto.discussionedit.OnlineDiscussionEditRequest
import com.on.network.dto.discussionlookup.DiscussionCursorPageResponse
import com.on.network.dto.discussionlookup.DiscussionQuery
import com.on.network.dto.discussionsummary.DiscussionSummaryRequest
import com.on.network.dto.discussionsummary.DiscussionSummaryResponse
import com.on.network.service.DiscussionService

internal class DiscussionRemoteDatasource(
    private val discussionService: DiscussionService,
) : DiscussionDatasource {
    override suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse> =
        safeApiCall { discussionService.getDiscussionDetail(id = id) }

    override suspend fun getDiscussions(
        query: DiscussionQuery,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCursorPageResponse> =
        safeApiCall {
            discussionService.getDiscussions(
                query = query.toQueryMap(),
                cursor = cursor,
                size = size,
            )
        }

    override suspend fun getMyDiscussions(
        cursor: String?,
        size: Int,
    ): Result<DiscussionCursorPageResponse> =
        safeApiCall { discussionService.getMyDiscussions(cursor = cursor, size = size) }

    override suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        query: DiscussionQuery,
        cursor: String?,
        size: Int,
    ): Result<DiscussionCursorPageResponse> =
        safeApiCall {
            discussionService.searchDiscussions(
                searchBy = searchBy,
                keyword = keyword,
                query = query.toQueryMap(),
                cursor = cursor,
                size = size,
            )
        }

    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<OfflineDiscussionCreateResponse> =
        safeApiCall { discussionService.postOfflineDiscussion(request = request) }

    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<OnlineDiscussionCreateResponse> =
        safeApiCall { discussionService.postOnlineDiscussion(request = request) }

    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> =
        safeApiCall { discussionService.postDiscussionSummary(request = request) }

    override suspend fun deleteDiscussion(id: Long): Result<Unit> =
        safeApiCall { discussionService.deleteDiscussion(id = id) }

    override suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOfflineDiscussion(id = id, request = request) }

    override suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOnlineDiscussion(id = id, request = request) }
}
