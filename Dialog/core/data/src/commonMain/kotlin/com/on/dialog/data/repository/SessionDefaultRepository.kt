package com.on.dialog.data.repository

import com.on.dialog.core.data.BuildKonfig
import com.on.dialog.core.local.datasourceimpl.LocalUserStorage
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.network.datasource.CookieStore
import com.on.dialog.network.dto.session.CookieNetworkEntity
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SessionDefaultRepository(
    private val cookieStore: CookieStore,
    private val localUserStorage: LocalUserStorage,
) : SessionRepository {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    override val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    override fun setLoggedIn(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }

    override suspend fun saveSession(
        jsessionId: String,
    ): Result<Unit> = runCatching {
        cookieStore.save(
            cookie = CookieNetworkEntity(
                name = JSESSIONID,
                value = jsessionId,
                domain = BuildKonfig.BASE_URL.toDomainUrl(),
                path = "/",
                secure = true,
                httpOnly = true,
            ),
        )
        _isLoggedIn.value = true
    }

    override suspend fun clearSession(): Result<Unit> = runCatching {
        cookieStore.clear()
        _isLoggedIn.value = false
    }

    override suspend fun hasValidSession(): Result<Boolean> = runCatching {
        // JSESSIONID 쿠키가 있는지 확인
        val url = Url(urlString = BuildKonfig.BASE_URL)
        val cookies: List<CookieNetworkEntity> = cookieStore.loadAll(
            requestHost = url.host,
            requestPath = url.encodedPath,
        )
        cookies.any { it.name == JSESSIONID }.also { isValid ->
            _isLoggedIn.value = isValid
        }
    }

    override suspend fun saveUserId(userId: Long): Result<Unit> = runCatching {
        localUserStorage.saveUserId(userId = userId)
    }

    override suspend fun getUserId(): Result<Long?> = runCatching {
        localUserStorage.getUserId()
    }

    override suspend fun clearUserId(): Result<Unit> = runCatching {
        localUserStorage.clearUserId()
    }

    companion object {
        private const val JSESSIONID = "JSESSIONID"

        private fun String.toDomainUrl(): String =
            this.substringAfter(delimiter = "https://").removeSuffix(suffix = "/")
    }
}
