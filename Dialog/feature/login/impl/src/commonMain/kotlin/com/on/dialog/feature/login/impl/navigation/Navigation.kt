package com.on.dialog.feature.login.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginNavKey
import com.on.dialog.feature.login.impl.LoginScreen
import com.on.dialog.feature.signup.api.SignUpNavKey
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.loginScreen(
    navigator: Navigator,
) {
    entry<LoginNavKey> {
        LoginScreen(
            goBack = navigator::goBack,
            navigateToSignUp = { jsessionId: String ->
                navigator.goBack()
                navigator.navigate(SignUpNavKey(jsessionId = jsessionId))
            },
        )
    }
}
