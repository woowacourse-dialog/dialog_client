package com.on.dialog.feature.signup.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent

sealed interface SignUpIntent : UiIntent {
    data class SelectTrack(
        val index: Int,
    ) : SignUpIntent

    data class ToggleNotification(
        val enabled: Boolean,
    ) : SignUpIntent

    data object ValidateAndSignUp : SignUpIntent
}
