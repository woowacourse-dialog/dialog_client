package com.on.dialog.feature.login.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.LoginType
import com.on.dialog.feature.login.LoginWebViewScreen
import com.on.dialog.feature.login.api.LoginRoute
import com.on.navigation.Navigator

fun EntryProviderScope<NavKey>.loginScreen(
    navigator: Navigator,
) {
    entry<LoginRoute> {
        LoginWebViewScreen(
            loginType = LoginType.GITHUB,
            goBack = navigator::goBack,
            // TODO 추후에 회원가입 화면으로 이동
            navigateToSignUp = navigator::goBack,
        )
    }
}
