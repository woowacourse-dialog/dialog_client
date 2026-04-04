package com.on.dialog.domain.repository

import com.on.dialog.model.auth.OAuthLoginResult
import com.on.dialog.model.common.Track

interface AuthRepository {
    suspend fun signup(
        track: Track,
        webPushNotification: Boolean,
    ): Result<Long>

    suspend fun getLoginStatus(): Result<Boolean>

    suspend fun logout(): Result<Unit>

    suspend fun postAppleLogin(
        identityToken: String,
        firstName: String?,
        lastName: String?,
    ): Result<OAuthLoginResult>
}
