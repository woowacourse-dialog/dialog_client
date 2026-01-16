package com.on.network.service

import com.on.network.dto.auth.LoginCheckResponse
import com.on.network.dto.auth.SignupRequest
import com.on.network.dto.auth.SignupResponse
import com.on.network.dto.common.DataResponse
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
    suspend fun logout(): DataResponse<Unit?>
}
