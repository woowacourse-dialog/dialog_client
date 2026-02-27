package com.on.dialog.feature.login.api.event

import kotlinx.coroutines.flow.Flow

interface AuthEventBus {
    val events: Flow<AuthEvent>

    suspend fun emit(event: AuthEvent)
}

sealed interface AuthEvent {
    data object LogIn : AuthEvent

    data object LogOut : AuthEvent
}
