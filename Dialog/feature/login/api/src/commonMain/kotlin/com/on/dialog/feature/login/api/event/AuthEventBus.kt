package com.on.dialog.feature.login.api.event

import kotlinx.coroutines.flow.Flow

/**
 * AuthEventBus는 인증(Authentication) 상태 변경 이벤트를
 * Feature 간에 브로드캐스트하기 위한 이벤트 스트림 인터페이스입니다.
 *
 * 로그인 또는 로그아웃과 같이 인증 상태가 변경되는 시점에
 * 해당 이벤트를 emit하여, 이를 구독하고 있는 여러 ViewModel 또는
 * Feature 모듈이 상태를 동기화할 수 있도록 합니다.
 *
 * ⚠️ 이 구조는 "상태 저장"이 아니라 "상태 변경 알림"을 위한 이벤트 버스입니다.
 * 현재 로그인 여부 자체를 보관하는 용도가 아니라,
 * 로그인/로그아웃이 발생했음을 알리는 브로드캐스트 용도로 사용됩니다.
 *
 * // 이벤트 발생
 * authEventBus.emit(AuthEvent.LogIn)
 *
 * // 이벤트 구독
 * viewModelScope.launch {
 *     authEventBus.events.collect { event ->
 *         when (event) {
 *             AuthEvent.LogIn -> refresh()
 *             AuthEvent.LogOut -> clearState()
 *         }
 *     }
 * }
 */

interface AuthEventBus {
    val events: Flow<AuthEvent>

    suspend fun emit(event: AuthEvent)
}

sealed interface AuthEvent {
    data object LogIn : AuthEvent

    data object LogOut : AuthEvent
}
