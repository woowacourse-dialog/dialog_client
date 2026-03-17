package com.on.dialog.core.local.datasourceimpl

import com.google.firebase.messaging.FirebaseMessaging
import com.on.dialog.core.local.datasource.FcmTokenProvider
import kotlinx.coroutines.tasks.await

class AndroidFcmTokenProvider : FcmTokenProvider {
    override suspend fun getToken(): String = FirebaseMessaging.getInstance().token.await()
}
