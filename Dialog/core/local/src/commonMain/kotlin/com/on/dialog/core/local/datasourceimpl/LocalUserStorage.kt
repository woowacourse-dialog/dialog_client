package com.on.dialog.core.local.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalUserStorage(
    private val dataStore: DataStore<Preferences>,
) {
    private val userIdKey = longPreferencesKey(name = "user_id")
    private val fcmTokenKey = stringPreferencesKey(name = "fcm_token")
    private val fcmTokenIdKey = longPreferencesKey(name = "fcm_token_id")

    suspend fun saveUserId(userId: Long) {
        dataStore.edit { preferences ->
            preferences[userIdKey] = userId
        }
    }

    suspend fun getUserId(): Long? = dataStore.data
        .map { preferences ->
            preferences[userIdKey]
        }.first()

    suspend fun clearUserId() {
        dataStore.edit { preferences ->
            preferences.remove(userIdKey)
        }
    }

    suspend fun saveFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[fcmTokenKey] = token
        }
    }

    suspend fun getFcmToken(): String? = dataStore.data
        .map { preferences ->
            preferences[fcmTokenKey]
        }.first()

    suspend fun saveFcmTokenId(tokenId: Long) {
        dataStore.edit { preferences ->
            preferences[fcmTokenIdKey] = tokenId
        }
    }

    suspend fun getFcmTokenId(): Long? = dataStore.data
        .map { preferences ->
            preferences[fcmTokenIdKey]
        }.first()

    suspend fun clearFcmToken() {
        dataStore.edit { preferences ->
            preferences.remove(fcmTokenKey)
            preferences.remove(fcmTokenIdKey)
        }
    }
}
