package com.on.dialog.feature.signup.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.signup.api.SignUpNavKey
import com.on.dialog.feature.signup.impl.SignUpScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.signUpScreen(
    navigator: Navigator,
) {
    entry<SignUpNavKey> {
        SignUpScreen()
    }
}
