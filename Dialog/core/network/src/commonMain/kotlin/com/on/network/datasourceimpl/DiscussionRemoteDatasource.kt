package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionDatasource
import com.on.network.dto.query.DiscussionQuery
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse
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

    override suspend fun deleteDiscussion(id: Long): Result<Unit> =
        safeApiCall { discussionService.deleteDiscussion(id) }
}
