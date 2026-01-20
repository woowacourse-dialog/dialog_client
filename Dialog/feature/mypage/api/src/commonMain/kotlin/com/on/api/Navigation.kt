package com.on.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MyPageRoute : NavKey

/**
 * MyPage Navigator Interface
 * 다른 feature에서 마이페이지로 이동할 때 사용
 */
interface MyPageNavigator {
    fun navigateToMyPage()
}
