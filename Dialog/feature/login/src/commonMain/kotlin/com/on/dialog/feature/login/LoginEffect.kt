package com.on.dialog.feature.login

import com.on.dialog.ui.viewmodel.UiEffect

sealed interface LoginEffect : UiEffect {
    data object GoBack : LoginEffect

    data object NavigateToSignUp : LoginEffect


    data class ShowError(
        val message: String,
    ) : LoginEffect
}
