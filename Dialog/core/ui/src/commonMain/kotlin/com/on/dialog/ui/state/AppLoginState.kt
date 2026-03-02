package com.on.dialog.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class AppLoginStateHolder {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    fun setLoggedIn(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }
}

val LocalAppLoginStateHolder = staticCompositionLocalOf<AppLoginStateHolder> {
    error("LocalAppLoginStateHolder is not provided")
}
