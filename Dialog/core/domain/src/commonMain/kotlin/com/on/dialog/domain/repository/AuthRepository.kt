package com.on.dialog.domain.repository

import com.on.dialog.model.common.Track

interface AuthRepository {
    suspend fun signup(
        track: com.on.dialog.model.common.Track,
        webPushNotification: Boolean,
    ): Result<Long>

    suspend fun getLoginStatus(): Result<Boolean>

    suspend fun logout(): Result<Unit>
}
