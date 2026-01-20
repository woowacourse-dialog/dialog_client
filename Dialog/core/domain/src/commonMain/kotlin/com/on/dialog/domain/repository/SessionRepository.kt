package com.on.dialog.domain.repository

interface SessionRepository {
    suspend fun saveSession(jsessionId: String): Result<Unit>

    suspend fun clearSession(): Result<Unit>

    suspend fun hasValidSession(): Result<Boolean>
}
