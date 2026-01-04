package com.on.network.service

import com.on.network.dto.response.common.DataResponse
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.QueryMap

interface DiscussionService {
    @GET("api/discussions/{id}")
    suspend fun getDiscussionDetail(
        @Path("id") id: Long,
    ): Response<DataResponse<DiscussionDetailResponse>>

    @GET("api/discussions")
    suspend fun getDiscussions(
        @QueryMap query: Map<String, List<String>>,
    ): Response<DataResponse<DiscussionCursorPageResponse>>

    @GET("api/discussions/me")
    suspend fun getMyDiscussions(): Response<DataResponse<DiscussionCursorPageResponse>>

    @GET("api/discussions/search")
    suspend fun searchDiscussions(
        @Query searchBy: Int,
        @Query keyword: String,
        @QueryMap query: Map<String, List<String>>,
    ): Response<DataResponse<DiscussionCursorPageResponse>>

    @DELETE("api/discussions/{id}")
    suspend fun deleteDiscussion(
        @Path("id") id: Long,
    ): Response<DataResponse<Unit>>
}
