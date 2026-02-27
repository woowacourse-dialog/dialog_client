package com.on.dialog.data.event

import com.on.dialog.domain.event.AuthEvent
import com.on.dialog.domain.event.AuthEventBus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class DefaultAuthEventBus : AuthEventBus {
    private val _events = MutableSharedFlow<AuthEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val events: Flow<AuthEvent> = _events.asSharedFlow()

    override suspend fun emit(event: AuthEvent) {
        _events.tryEmit(event)
    }
}
