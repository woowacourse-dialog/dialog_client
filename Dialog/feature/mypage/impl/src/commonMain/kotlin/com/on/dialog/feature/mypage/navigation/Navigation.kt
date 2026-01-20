package com.on.dialog.feature.mypage.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.api.LoginRoute
import com.on.api.MyPageRoute
import com.on.dialog.feature.mypage.MyPageScreen
import com.on.navigation.Navigator

fun EntryProviderScope<NavKey>.myPageScreen(
    navigator: Navigator
) {
    entry<MyPageRoute> {
        MyPageScreen(
            navigateToLogin = { navigator.navigate(LoginRoute) }
        )
    }
}
