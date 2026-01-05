package com.on.network.service

import com.on.network.dto.common.DataResponse
import com.on.network.dto.discussioncreate.CreateOfflineDiscussionRequest
import com.on.network.dto.discussioncreate.CreateOnlineDiscussionRequest
import com.on.network.dto.discussioncreate.DiscussionCreateResponse
import com.on.network.dto.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.discussionedit.OfflineDiscussionEditRequest
import com.on.network.dto.discussionedit.OnlineDiscussionEditRequest
import com.on.network.dto.discussionlookup.DiscussionCursorPageResponse
import com.on.network.dto.discussionsummary.DiscussionSummaryRequest
import com.on.network.dto.discussionsummary.DiscussionSummaryResponse
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
