package com.on.dialog.feature.signup.impl.viewmodel

import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.UiIntent

interface SignUpIntent : UiIntent {
    data class Signup(
        val track: Track,
        val isNotificationEnabled: Boolean,
    ) : SignUpIntent
}