package com.on.dialog.data.repository

import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.model.auth.OAuthLoginResult
import com.on.dialog.model.common.Track
import com.on.dialog.network.datasource.AuthDatasource
import com.on.dialog.network.dto.auth.AppleLoginRequest
import com.on.dialog.network.dto.auth.LoginCheckResponse
import com.on.dialog.network.dto.auth.OAuthLoginResponse
import com.on.dialog.network.dto.auth.SignupRequest
import com.on.dialog.network.dto.auth.SignupResponse

internal class AuthDefaultRepository(
    private val authDatasource: AuthDatasource,
) : AuthRepository {
    override suspend fun signup(
        track: Track,
        webPushNotification: Boolean,
    ): Result<Long> =
        authDatasource
            .signup(
                request = SignupRequest(
                    track = track.name,
                    webPushNotification = webPushNotification,
                ),
            ).map { signUpResponse: SignupResponse -> signUpResponse.userId }

    override suspend fun getLoginStatus(): Result<Boolean> =
        authDatasource
            .getLoginStatus()
            .map { loginCheckResponse: LoginCheckResponse -> loginCheckResponse.isLoggedIn }

    override suspend fun logout(): Result<Unit> = authDatasource.logout()

    override suspend fun postAppleLogin(
        identityToken: String,
        firstName: String?,
        lastName: String?,
    ): Result<OAuthLoginResult> = authDatasource
        .postAppleLogin(
            request = AppleLoginRequest(
                identityToken = identityToken,
                firstName = firstName,
                lastName = lastName,
            ),
        ).map { loginResponse: OAuthLoginResponse -> loginResponse.toDomain() }
}
