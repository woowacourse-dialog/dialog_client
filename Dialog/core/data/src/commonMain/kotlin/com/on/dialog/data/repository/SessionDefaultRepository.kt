package com.on.dialog.data.repository

import com.on.dialog.core.data.BuildKonfig
import com.on.dialog.domain.repository.SessionRepository
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url

internal class SessionDefaultRepository(
    private val cookiesStorage: CookiesStorage,
) : SessionRepository {
    override suspend fun saveSession(
        requestUrl: String,
        jsessionId: String,
    ): Result<Unit> = runCatching {
        cookiesStorage.addCookie(
            requestUrl = Url(requestUrl),
            cookie = Cookie(
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
        // PersistentCookieStorage에 clear 메서드 추가 필요
        // 현재는 close() 호출
        cookiesStorage.close()
    }

    override suspend fun hasValidSession(): Result<Boolean> = runCatching {
        // JSESSIONID 쿠키가 있는지 확인
        val cookies = cookiesStorage.get(Url(BuildKonfig.BASE_URL))
        cookies.any { it.name == JSESSIONID }
    }

    companion object {
        private const val JSESSIONID = "JSESSIONID"

        private fun String.toDomainUrl(): String = this.substringAfter("https://").removeSuffix("/")
    }
}
