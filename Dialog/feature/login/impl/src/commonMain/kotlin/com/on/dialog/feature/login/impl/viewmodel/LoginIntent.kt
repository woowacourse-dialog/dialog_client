package com.on.dialog.feature.login.impl.viewmodel

import com.on.dialog.feature.login.impl.model.LoginError
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.ui.viewmodel.UiIntent

sealed interface LoginIntent : UiIntent {
    data class LoginSuccess(
        val jsessionId: String,
        val isNewUser: Boolean = false,
    ) : LoginIntent

    data class LoginFailure(
        val loginError: LoginError,
    ) : LoginIntent

    data class SelectLoginType(
        val loginType: LoginType,
    ) : LoginIntent
}
