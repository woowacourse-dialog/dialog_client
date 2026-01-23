package com.on.dialog.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

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
