package com.on.dialog.core.local.datasource

internal interface FcmTokenProvider {
    suspend fun getToken(): String
}
