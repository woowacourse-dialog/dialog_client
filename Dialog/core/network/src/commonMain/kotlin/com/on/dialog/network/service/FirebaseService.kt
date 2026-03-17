package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.firebase.MyTokenResponse
import com.on.dialog.network.dto.firebase.TokenCreationResponse
import com.on.dialog.network.dto.firebase.TokenRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface FirebaseService {
    @GET("api/fcm-tokens")
    suspend fun getFcmToken(): DataResponse<MyTokenResponse>

    @POST("api/fcm-tokens")
    suspend fun postFcmToken(
        @Body tokenRequest: TokenRequest,
    ): DataResponse<TokenCreationResponse>

    @PATCH("api/fcm-tokens/{tokenId}")
    suspend fun patchFcmToken(
        @Path("tokenId") tokenId: Long,
        @Body tokenRequest: TokenRequest,
    ): DataResponse<Unit>

    @DELETE("api/fcm-tokens/{tokenId}")
    suspend fun deleteFcmToken(
        @Path("tokenId") tokenId: Long,
    ): DataResponse<Unit>
}
