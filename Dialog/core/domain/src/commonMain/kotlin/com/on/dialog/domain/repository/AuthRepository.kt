package com.on.dialog.domain.repository

import com.on.model.common.Track

interface AuthRepository {
    suspend fun signup(track: Track, webPushNotification: Boolean): Result<Long>

    suspend fun getLoginStatus(): Result<Boolean>

    suspend fun logout(): Result<Unit>
}
