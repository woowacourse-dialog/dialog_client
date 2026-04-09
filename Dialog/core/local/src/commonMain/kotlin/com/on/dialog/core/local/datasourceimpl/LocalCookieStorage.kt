package com.on.dialog.core.local.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.on.dialog.core.local.dto.session.CookieLocalEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

class LocalCookieStorage(
    private val dataStore: DataStore<Preferences>,
) {
    private val mutex = Mutex()
    private val cookiesKey = stringPreferencesKey(name = "http_cookies")
    private val cookiesCache = mutableMapOf<String, CookieLocalEntity>()

    suspend fun save(cookie: CookieLocalEntity) {
        mutex.withLock {
            cookiesCache[cookie.name] = cookie
            saveCookies()
        }
    }

    suspend fun loadAll(requestHost: String, requestPath: String): List<CookieLocalEntity> {
        mutex.withLock {
            loadCookiesIfNeeded()

            return cookiesCache.values.filter { cookie ->
                // 유효성 검사
                cookie.matches(requestHost = requestHost, requestPath = requestPath)
            }
        }
    }

    suspend fun clear() {
        mutex.withLock {
            cookiesCache.clear()
            dataStore.edit { it.clear() }
        }
    }

    private suspend fun loadCookiesIfNeeded() {
        if (cookiesCache.isEmpty()) {
            val stored: String? = dataStore.data.first()[cookiesKey]
            stored?.let {
                val cookies: List<CookieLocalEntity> = Json.decodeFromString(string = stored)
                cookies.forEach {
                    cookiesCache[it.name] = it
                }
            }
        }
    }

    private suspend fun saveCookies() {
        val cookies: List<CookieLocalEntity> = cookiesCache.values.map { it }
        val json: String = Json.encodeToString(value = cookies)
        dataStore.edit { it[cookiesKey] = json }
    }

    private fun CookieLocalEntity.matches(requestHost: String, requestPath: String): Boolean {
        // Domain 검사
        val cookieDomain: String = domain.lowercase()
        val requestDomain: String = requestHost.lowercase()
        if (cookieDomain.isNotEmpty() && !requestDomain.endsWith(suffix = cookieDomain)) {
            return false
        }

        // Path 체크
        val cookiePath: String = path
        if (!requestPath.startsWith(prefix = cookiePath)) {
            return false
        }
        return true
    }
}
