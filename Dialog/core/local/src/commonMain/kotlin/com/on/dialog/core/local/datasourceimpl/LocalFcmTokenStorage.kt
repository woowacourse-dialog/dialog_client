package com.on.dialog.core.local.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.on.dialog.core.local.datasource.LocalPushTokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalFcmTokenStorage(
    private val dataStore: DataStore<Preferences>,
) : LocalPushTokenStorage {
    private val fcmTokenIdKey = longPreferencesKey(name = "fcm_token_id")

    override suspend fun savePushTokenId(tokenId: Long) {
        dataStore.edit { preferences ->
            preferences[fcmTokenIdKey] = tokenId
        }
    }

    override suspend fun getPushTokenId(): Long? = dataStore.data
        .map { preferences ->
            preferences[fcmTokenIdKey]
        }.first()

    override suspend fun clearPushToken() {
        dataStore.edit { preferences ->
            preferences.remove(fcmTokenIdKey)
        }
    }
}
