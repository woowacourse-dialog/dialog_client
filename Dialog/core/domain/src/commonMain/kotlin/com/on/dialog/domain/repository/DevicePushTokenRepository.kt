package com.on.dialog.domain.repository

interface DevicePushTokenRepository {
    suspend fun getDeviceToken(): String

    suspend fun savePushTokenId(tokenId: Long)

    suspend fun getPushTokenId(): Long?

    suspend fun clearPushToken()
}
