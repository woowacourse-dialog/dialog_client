package com.on.dialog.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.appScreens(
    navigator: Navigator,
    providers: List<NavKeyProvider>,
) {
    providers.forEach { provider ->
        with(provider) {
            registerScreens(navigator)
        }
    }
}
