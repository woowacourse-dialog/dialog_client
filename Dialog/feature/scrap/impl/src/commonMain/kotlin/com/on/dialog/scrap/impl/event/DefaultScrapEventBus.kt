package com.on.dialog.scrap.impl.event

import com.on.dialog.feature.scrap.api.event.ScrapEvent
import com.on.dialog.feature.scrap.api.event.ScrapEventBus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class DefaultScrapEventBus : ScrapEventBus {
    private val _events = MutableSharedFlow<ScrapEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val events: Flow<ScrapEvent> = _events.asSharedFlow()

    override suspend fun emit(event: ScrapEvent) {
        _events.tryEmit(event)
    }
}
