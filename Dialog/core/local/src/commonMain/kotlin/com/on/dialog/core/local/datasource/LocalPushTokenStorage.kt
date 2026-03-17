package com.on.dialog.core.local.datasource

interface LocalPushTokenStorage {
    suspend fun savePushTokenId(tokenId: Long)

    suspend fun getPushTokenId(): Long?

    suspend fun clearPushToken()
}
