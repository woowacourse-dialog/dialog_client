package com.on.dialog.core.local.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalUserStorage(
    private val dataStore: DataStore<Preferences>,
) {
    private val userIdKey = longPreferencesKey(name = "user_id")

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
}
