package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.AuthDatasource
import com.on.network.dto.auth.LoginCheckResponse
import com.on.network.dto.auth.SignupRequest
import com.on.network.dto.auth.SignupResponse
import com.on.network.service.AuthService

internal class AuthRemoteDatasource(
    private val authService: AuthService,
) : AuthDatasource {
    override suspend fun signup(request: SignupRequest): Result<SignupResponse> =
        safeApiCall { authService.signup(request) }

    override suspend fun getLoginStatus(): Result<LoginCheckResponse> =
        safeApiCall { authService.getLoginStatus() }

    override suspend fun logout(): Result<Unit> = safeApiCall { authService.logout() }
}
