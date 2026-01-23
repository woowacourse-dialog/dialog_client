package com.on.dialog.network.service

import com.on.dialog.network.dto.auth.LoginCheckResponse
import com.on.dialog.network.dto.auth.SignupRequest
import com.on.dialog.network.dto.auth.SignupResponse
import com.on.dialog.network.dto.common.DataResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun signup(
        @Body request: SignupRequest,
    ): DataResponse<SignupResponse>

    @GET("api/login/check")
    suspend fun getLoginStatus(): DataResponse<LoginCheckResponse>

    @DELETE("api/logout")
    suspend fun logout(): DataResponse<Unit>
}
