package com.on.dialog.feature.login.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class LoginState(
    val loginType: LoginType = LoginType.NONE,
    val isLoading: Boolean = false,
    val isLoginComplete: Boolean = false,
    val isNewUser: Boolean = false,
) : UiState
