package com.on.dialog.feature.signup.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class SignUpState(
    val selectedTrackIndex: Int? = null,
    val isTrackSelected: Boolean? = null,
    val isNotificationEnabled: Boolean = false,
) : UiState
