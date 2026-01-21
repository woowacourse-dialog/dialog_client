package com.on.dialog.feature.mypage.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginNavKey
import com.on.dialog.feature.mypage.MyPageScreen
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.navigation.Navigator

fun EntryProviderScope<NavKey>.myPageScreen(
    navigator: Navigator,
) {
    entry<MyPageNavKey> {
        MyPageScreen(
            navigateToLogin = { navigator.navigate(LoginNavKey) },
        )
    }
}
