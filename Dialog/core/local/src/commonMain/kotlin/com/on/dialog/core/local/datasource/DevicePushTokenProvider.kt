package com.on.dialog.core.local.datasource

interface DevicePushTokenProvider {
    suspend fun getDeviceToken(): String
}
