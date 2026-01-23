package com.on.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.api.DiscussionDetailNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import com.on.impl.ScrapScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.scrapScreen(
    navigator: Navigator,
) {
    entry<ScrapNavKey> {
        ScrapScreen(navigateToDiscussionDetail = { navigator.navigate(DiscussionDetailNavKey) })
    }
}
