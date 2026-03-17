package com.on.dialog.data.repository

import com.on.dialog.core.local.datasource.DevicePushTokenProvider
import com.on.dialog.core.local.datasource.LocalPushTokenStorage
import com.on.dialog.domain.repository.DevicePushTokenRepository

class DevicePushTokenDefaultRepository(
    private val devicePushTokenProvider: DevicePushTokenProvider,
    private val localPushTokenStorage: LocalPushTokenStorage,
) : DevicePushTokenRepository {
    override suspend fun getDeviceToken(): String = devicePushTokenProvider.getDeviceToken()

    override suspend fun savePushTokenId(tokenId: Long) {
        localPushTokenStorage.savePushTokenId(tokenId = tokenId)
    }

    override suspend fun getPushTokenId(): Long? = localPushTokenStorage.getPushTokenId()

    override suspend fun clearPushToken() {
        localPushTokenStorage.clearPushToken()
    }
}
