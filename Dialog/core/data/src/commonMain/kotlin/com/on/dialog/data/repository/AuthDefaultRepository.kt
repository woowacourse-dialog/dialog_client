package com.on.dialog.data.repository

import com.on.dialog.domain.repository.AuthRepository
import com.on.model.common.Track
import com.on.network.datasource.AuthDatasource
import com.on.network.dto.auth.SignupRequest

internal class AuthDefaultRepository(
    private val authDatasource: AuthDatasource,
) : AuthRepository {
    override suspend fun signup(
        track: Track,
        webPushNotification: Boolean,
    ): Result<Long> =
        authDatasource
            .signup(SignupRequest(track = track.name, webPushNotification = webPushNotification))
            .map { it.userId }

    override suspend fun getLoginStatus(): Result<Boolean> =
        authDatasource.getLoginStatus().map { it.isLoggedIn }

    override suspend fun logout(): Result<Unit> = authDatasource.logout()
}
