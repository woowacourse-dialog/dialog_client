package com.on.dialog.feature.login

import com.on.dialog.ui.viewmodel.UiIntent

/**
 * 로그인 화면에서 발생하는 사용자의 의도(Intent)를 정의하는 인터페이스입니다.
 *
 * @property LoginVia 특정 로그인 방식을 통한 로그인 프로세스를 시작합니다.
 * @property CancelLogin 로그인 과정을 취소합니다.
 * @property LoginSuccess 로그인 성공 시 세션 정보를 저장합니다.
 * @property LoginFailure 로그인 실패 상황을 처리합니다.
kotlin
 */
sealed interface LoginIntent : UiIntent {
    data class LoginVia(
        val loginType: LoginType,
    ) : LoginIntent

    data object CancelLogin : LoginIntent

    data class LoginSuccess(
        val jsessionId: String,
        val isNewUser: Boolean = false,
    ) : LoginIntent

    data object LoginFailure : LoginIntent
}
