package com.on.dialog.feature.signup.impl.viewmodel

import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.UiIntent

sealed interface SignUpIntent : UiIntent {
    data object CancelSignUp : SignUpIntent

    data class SelectTrack(
        val track: Track,
    ) : SignUpIntent

    data class ToggleNotification(
        val enabled: Boolean,
    ) : SignUpIntent

    data class ValidateAndSignUp(
        val jsessionId: String,
    ) : SignUpIntent
}
