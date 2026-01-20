package com.on.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute : NavKey

/**
 * Main Navigator Interface
 * 다른 feature에서 메인 화면으로 이동할 때 사용
 */
interface MainNavigator {
    fun navigateToMain()
}
