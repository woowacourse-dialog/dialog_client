package com.on.dialog.feature.login.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginNavKey
import com.on.dialog.feature.login.impl.LoginWebViewScreen
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.signup.api.SignUpNavKey
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.loginScreen(
    navigator: Navigator,
) {
    entry<LoginNavKey> {
        LoginWebViewScreen(
            loginType = LoginType.GITHUB,
            goBack = navigator::goBack,
            navigateToSignUp = {
                navigator.goBack()
                navigator.navigate(SignUpNavKey)
            },
        )
    }
}
