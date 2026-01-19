package com.on.dialog.data.repository

import com.on.dialog.core.data.BuildKonfig
import com.on.dialog.domain.repository.SessionRepository
import com.on.network.datasource.CookieStore
import com.on.network.dto.session.CookieNetworkEntity
import io.ktor.http.Url

internal class SessionDefaultRepository(
    private val cookieStore: CookieStore,
) : SessionRepository {
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
    }

    override suspend fun clearSession(): Result<Unit> = runCatching {
        cookieStore.clear()
    }

    override suspend fun hasValidSession(): Result<Boolean> = runCatching {
        // JSESSIONID 쿠키가 있는지 확인
        val url = Url(urlString = BuildKonfig.BASE_URL)
        val cookies: List<CookieNetworkEntity> = cookieStore.loadAll(
            requestHost = url.host,
            requestPath = url.encodedPath,
        )
        cookies.any { it.name == JSESSIONID }
    }

    companion object {
        private const val JSESSIONID = "JSESSIONID"

        private fun String.toDomainUrl(): String =
            this.substringAfter(delimiter = "https://").removeSuffix(suffix = "/")
    }
}
