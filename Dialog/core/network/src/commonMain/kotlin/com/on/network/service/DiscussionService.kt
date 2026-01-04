package com.on.network.service

import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.request.OfflineDiscussionEditRequest
import com.on.network.dto.request.OnlineDiscussionEditRequest
import com.on.network.dto.response.common.DataResponse
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.QueryMap

interface DiscussionService {
    @POST("api/discussions/offline")
    suspend fun postOfflineDiscussion(
        @Body request: CreateOfflineDiscussionRequest,
    ): Response<DataResponse<DiscussionCreateResponse>>

    @POST("api/discussions/online")
    suspend fun postOnlineDiscussion(
        @Body request: CreateOnlineDiscussionRequest,
    ): Response<DataResponse<DiscussionCreateResponse>>

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

    @PATCH("api/discussions/offline/{id}")
    suspend fun updateOfflineDiscussion(
        @Path("id") id: Long,
        @Body request: OfflineDiscussionEditRequest,
    ): Response<DataResponse<Unit>>

    @PATCH("api/discussions/online/{id}")
    suspend fun updateOnlineDiscussion(
        @Path("id") id: Long,
        @Body request: OnlineDiscussionEditRequest,
    ): Response<DataResponse<Unit>>

    @DELETE("api/discussions/{id}")
    suspend fun deleteDiscussion(
        @Path("id") id: Long,
    ): Response<DataResponse<Unit>>

    @POST("api/discussions/summary")
    suspend fun postDiscussionSummary(
        @Body request: DiscussionSummaryRequest,
    ): Response<DataResponse<DiscussionSummaryResponse>>
}
