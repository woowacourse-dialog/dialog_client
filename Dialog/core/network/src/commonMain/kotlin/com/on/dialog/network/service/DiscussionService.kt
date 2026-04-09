package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.discussioncreate.CreateOfflineDiscussionRequest
import com.on.dialog.network.dto.discussioncreate.CreateOnlineDiscussionRequest
import com.on.dialog.network.dto.discussioncreate.OfflineDiscussionCreateResponse
import com.on.dialog.network.dto.discussioncreate.OnlineDiscussionCreateResponse
import com.on.dialog.network.dto.discussiondetail.DiscussionDetailResponse
import com.on.dialog.network.dto.discussionedit.OfflineDiscussionEditRequest
import com.on.dialog.network.dto.discussionedit.OnlineDiscussionEditRequest
import com.on.dialog.network.dto.discussionlookup.DiscussionCursorPageResponse
import com.on.dialog.network.dto.discussionsummary.DiscussionSummaryRequest
import com.on.dialog.network.dto.discussionsummary.DiscussionSummaryResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.QueryMap

internal interface DiscussionService {
    @POST("api/discussions/offline")
    suspend fun postOfflineDiscussion(
        @Body request: CreateOfflineDiscussionRequest,
    ): DataResponse<OfflineDiscussionCreateResponse>

    @POST("api/discussions/online")
    suspend fun postOnlineDiscussion(
        @Body request: CreateOnlineDiscussionRequest,
    ): DataResponse<OnlineDiscussionCreateResponse>

    @GET("api/discussions/{id}")
    suspend fun getDiscussionDetail(
        @Path("id") id: Long,
    ): DataResponse<DiscussionDetailResponse>

    @GET("api/discussions")
    suspend fun getDiscussions(
        @QueryMap query: Map<String, String>,
        @Query cursor: String?,
        @Query size: Int,
    ): DataResponse<DiscussionCursorPageResponse>

    @GET("api/discussions/me")
    suspend fun getMyDiscussions(
        @Query cursor: String?,
        @Query size: Int,
    ): DataResponse<DiscussionCursorPageResponse>

    @GET("api/discussions/search")
    suspend fun searchDiscussions(
        @Query searchBy: Int,
        @Query keyword: String,
        @QueryMap query: Map<String, String>,
        @Query cursor: String?,
        @Query size: Int,
    ): DataResponse<DiscussionCursorPageResponse>

    @PATCH("api/discussions/offline/{id}")
    suspend fun updateOfflineDiscussion(
        @Path("id") id: Long,
        @Body request: OfflineDiscussionEditRequest,
    ): DataResponse<Unit>

    @PATCH("api/discussions/online/{id}")
    suspend fun updateOnlineDiscussion(
        @Path("id") id: Long,
        @Body request: OnlineDiscussionEditRequest,
    ): DataResponse<Unit>

    @DELETE("api/discussions/{id}")
    suspend fun deleteDiscussion(
        @Path("id") id: Long,
    ): DataResponse<Unit>

    @POST("api/discussions/summary")
    suspend fun postDiscussionSummary(
        @Body request: DiscussionSummaryRequest,
    ): DataResponse<DiscussionSummaryResponse>
}
