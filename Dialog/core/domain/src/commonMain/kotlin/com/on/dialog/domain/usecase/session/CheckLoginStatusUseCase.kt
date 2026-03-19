package com.on.dialog.domain.usecase.session

import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.SessionRepository

/**
 * 서버에 실제 세션 유효성을 확인하고 로컬 로그인 상태를 동기화하는 UseCase
 *
 * 앱 최초 진입 시 [SessionRepository.isLoggedIn]이 null인 상태에서 호출하여
 * 로컬에 저장된 JSESSIONID가 서버에서도 유효한지 확인합니다.
 *
 * - 서버 응답이 true → [SessionRepository.setLoggedIn](true)
 * - 서버 응답이 false → [SessionRepository.clearSession] (쿠키 포함 초기화)
 * - 네트워크 오류 등 실패 → 상태 변경 없이 실패 Result 반환
 */
class CheckLoginStatusUseCase(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) {
    suspend operator fun invoke(): Result<Boolean> =
        authRepository.getLoginStatus()
            .onSuccess { isLoggedIn ->
                if (isLoggedIn) {
                    sessionRepository.setLoggedIn(true)
                } else {
                    sessionRepository.clearSession()
                }
            }
}
