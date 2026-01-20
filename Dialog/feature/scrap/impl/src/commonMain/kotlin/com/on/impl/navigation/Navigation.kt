package com.on.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.api.ScrapRoute
import com.on.impl.ScrapScreen
import com.on.navigation.Navigator

fun EntryProviderScope<NavKey>.scrapScreen(
    navigator: Navigator
) {
    entry<ScrapRoute> {
        ScrapScreen()
    }
}
