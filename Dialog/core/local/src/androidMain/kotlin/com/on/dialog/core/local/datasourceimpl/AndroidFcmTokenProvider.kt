package com.on.dialog.core.local.datasourceimpl

import com.google.firebase.messaging.FirebaseMessaging
import com.on.dialog.core.local.datasource.DevicePushTokenProvider
import kotlinx.coroutines.tasks.await

class AndroidFcmTokenProvider : DevicePushTokenProvider {
    override suspend fun getDeviceToken(): String = FirebaseMessaging.getInstance().token.await()
}
