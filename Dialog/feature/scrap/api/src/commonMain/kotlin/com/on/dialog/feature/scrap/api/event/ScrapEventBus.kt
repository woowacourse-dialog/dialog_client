package com.on.dialog.feature.scrap.api.event

import kotlinx.coroutines.flow.Flow

interface ScrapEventBus {
    val events: Flow<ScrapEvent>

    suspend fun emit(event: ScrapEvent)
}

sealed interface ScrapEvent {
    data class Added(
        val discussionId: Long,
    ) : ScrapEvent

    data class Removed(
        val discussionId: Long,
    ) : ScrapEvent
}
