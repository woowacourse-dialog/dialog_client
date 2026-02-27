package com.on.dialog.feature.scrap.api.event

import kotlinx.coroutines.flow.Flow

/**
 * ScrapEventBus는 스크랩(Scrap) 상태 변경 이벤트를
 * Feature 간에 브로드캐스트하기 위한 이벤트 스트림 인터페이스입니다.
 *
 * 특정 토론(discussion)의 스크랩이 추가되거나 제거되었을 때
 * 해당 이벤트를 emit하여, 이를 구독 중인 ViewModel 또는 화면이
 * 자신의 상태를 동기화할 수 있도록 합니다.
 *
 * ⚠️ 이 구조는 "스크랩 상태를 저장"하는 용도가 아니라,
 * "스크랩 상태가 변경되었음을 알리는 이벤트"를 전파하기 위한 버스입니다.
 *
 * // 이벤트 발생
 * scrapEventBus.emit(ScrapEvent.Added(discussionId))
 *
 * // 이벤트 구독
 * viewModelScope.launch {
 *     scrapEventBus.events.collect { event ->
 *         when (event) {
 *             is ScrapEvent.Added -> handleAdded(event.discussionId)
 *             is ScrapEvent.Removed -> handleRemoved(event.discussionId)
 *         }
 *     }
 * }
 */

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
