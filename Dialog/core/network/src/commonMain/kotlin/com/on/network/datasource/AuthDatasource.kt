package com.on.network.datasource

import com.on.network.dto.auth.LoginCheckResponse
import com.on.network.dto.auth.SignupRequest
import com.on.network.dto.auth.SignupResponse

interface AuthDatasource {
    suspend fun signup(request: SignupRequest): Result<SignupResponse>

    suspend fun getLoginStatus(): Result<LoginCheckResponse>

    suspend fun logout(): Result<Unit>
}
