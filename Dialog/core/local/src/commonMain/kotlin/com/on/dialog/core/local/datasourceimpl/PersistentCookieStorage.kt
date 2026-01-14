package com.on.dialog.core.local.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PersistentCookieStorage(
    private val dataStore: DataStore<Preferences>,
) : CookiesStorage {
    private val mutex = Mutex()
    private val cookiesKey = stringPreferencesKey("http_cookies")
    private val cookiesCache = mutableMapOf<String, Cookie>()

    init {
        // 앱 시작시 저장된 쿠키 로드
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        mutex.withLock {
            cookiesCache[cookie.name] = cookie
            saveCookies()
        }
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        mutex.withLock {
            loadCookiesIfNeeded()

            return cookiesCache.values.filter { cookie ->
                // 유효성 검사
                cookie.matches(requestUrl)
            }
        }
    }

    override fun close() {
        // clean up
    }

    private suspend fun loadCookiesIfNeeded() {
        if (cookiesCache.isEmpty()) {
            val stored: String? = dataStore.data.first()[cookiesKey]
            stored?.let {
                val cookies: List<SerializableCookie> = Json.decodeFromString(stored)
                cookies.forEach {
                    cookiesCache[it.name] = it.toCookie()
                }
            }
        }
    }

    private suspend fun saveCookies() {
        val cookies: List<SerializableCookie> = cookiesCache.values.map { it.toSerializable() }
        val json: String = Json.encodeToString(cookies)
        dataStore.edit { it[cookiesKey] = json }
    }
}

@Serializable
private data class SerializableCookie(
    val name: String,
    val value: String,
    val domain: String,
    val path: String,
    val expires: Long? = null,
) {
    fun toCookie(): Cookie = Cookie(
        name = name,
        value = value,
        domain = domain,
        path = path,
        expires = expires?.let { GMTDate(it) },
    )
}

private fun Cookie.toSerializable(): SerializableCookie = SerializableCookie(
    name = name,
    value = value,
    domain = domain ?: "",
    path = path ?: "/",
    expires = expires?.timestamp,
)

private fun Cookie.matches(requestUrl: Url): Boolean {
    // 만료 검사
    if (expires != null && expires!!.timestamp < GMTDate().timestamp) {
        return false
    }

    // Domain 검사
    val cookieDomain = domain?.lowercase() ?: ""
    val requestDomain = requestUrl.host.lowercase()
    if (cookieDomain.isNotEmpty() && !requestDomain.endsWith(cookieDomain)) {
        return false
    }

    // Path 체크
    val cookiePath = path ?: "/"
    if (!requestUrl.encodedPath.startsWith(cookiePath)) {
        return false
    }
    return true
}
