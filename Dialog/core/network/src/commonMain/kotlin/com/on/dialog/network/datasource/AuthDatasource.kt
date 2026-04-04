package com.on.dialog.network.datasource

import com.on.dialog.network.dto.auth.AppleLoginRequest
import com.on.dialog.network.dto.auth.LoginCheckResponse
import com.on.dialog.network.dto.auth.OAuthLoginResponse
import com.on.dialog.network.dto.auth.SignupRequest
import com.on.dialog.network.dto.auth.SignupResponse

interface AuthDatasource {
    suspend fun signup(request: SignupRequest): Result<SignupResponse>

    suspend fun getLoginStatus(): Result<LoginCheckResponse>

    suspend fun logout(): Result<Unit>

    suspend fun postAppleLogin(request: AppleLoginRequest): Result<OAuthLoginResponse>
}
