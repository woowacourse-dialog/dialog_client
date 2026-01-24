package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.AuthDatasource
import com.on.dialog.network.dto.auth.LoginCheckResponse
import com.on.dialog.network.dto.auth.SignupRequest
import com.on.dialog.network.dto.auth.SignupResponse
import com.on.dialog.network.service.AuthService

internal class AuthRemoteDatasource(
    private val authService: AuthService,
) : AuthDatasource {
    override suspend fun signup(request: SignupRequest): Result<SignupResponse> =
        safeApiCall { authService.signup(request = request) }

    override suspend fun getLoginStatus(): Result<LoginCheckResponse> =
        safeApiCall { authService.getLoginStatus() }

    override suspend fun logout(): Result<Unit> = safeApiCall { authService.logout() }
}
