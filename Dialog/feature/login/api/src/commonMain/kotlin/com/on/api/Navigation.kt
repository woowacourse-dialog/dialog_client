package com.on.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute : NavKey

/**
 * Login Navigator Interface
 * 다른 feature에서 로그인으로 이동할 때 사용
 */
interface LoginNavigator {
    fun navigateToLogin()
}
