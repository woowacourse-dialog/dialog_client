package com.on.dialog.feature.login.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginNavKey
import com.on.dialog.feature.login.impl.LoginType
import com.on.dialog.feature.login.impl.LoginWebViewScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.loginScreen(
    navigator: Navigator,
) {
    entry<LoginNavKey> {
        LoginWebViewScreen(
            loginType = LoginType.GITHUB,
            goBack = navigator::goBack,
            // TODO 추후에 회원가입 화면으로 이동
            navigateToSignUp = navigator::goBack,
        )
    }
}
