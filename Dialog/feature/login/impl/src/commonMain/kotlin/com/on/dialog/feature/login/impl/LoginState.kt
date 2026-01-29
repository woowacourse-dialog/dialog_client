package com.on.dialog.feature.login.impl

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class LoginState(
    val isLoading: Boolean = false,
    val isLoginComplete: Boolean = false,
    val isNewUser: Boolean = false,
) : UiState
