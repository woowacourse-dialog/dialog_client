package com.on.dialog.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val isLoggedIn: StateFlow<Boolean?>

    fun setLoggedIn(isLoggedIn: Boolean)

    suspend fun saveSession(jsessionId: String): Result<Unit>

    suspend fun clearSession(): Result<Unit>

    suspend fun hasValidSession(): Result<Boolean>

    suspend fun saveUserId(userId: Long): Result<Unit>

    suspend fun getUserId(): Result<Long?>

    suspend fun clearUserId(): Result<Unit>
}
