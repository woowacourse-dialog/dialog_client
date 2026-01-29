package com.on.dialog.network.datasource

import com.on.dialog.network.dto.auth.LoginCheckResponse
import com.on.dialog.network.dto.auth.SignupRequest
import com.on.dialog.network.dto.auth.SignupResponse

interface AuthDatasource {
    suspend fun signup(request: SignupRequest): Result<SignupResponse>

    suspend fun getLoginStatus(): Result<LoginCheckResponse>

    suspend fun logout(): Result<Unit>
}
