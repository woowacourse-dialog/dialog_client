package com.on.dialog.feature.login

import com.on.dialog.ui.viewmodel.UiEffect

sealed interface LoginEffect : UiEffect {
    data class OpenLoginWebView(
        val loginType: LoginType,
    ) : LoginEffect

    data object CloseLoginWebView : LoginEffect

    data class ShowError(
        val message: String,
    ) : LoginEffect
}
