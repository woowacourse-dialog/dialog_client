package com.on.dialog.feature.main.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.main.api.MainNavKey
import com.on.dialog.feature.main.impl.DiscussionListScreen
import com.on.navigation.Navigator

fun EntryProviderScope<NavKey>.mainScreen(
    navigator: Navigator,
) {
    entry<MainNavKey> {
        DiscussionListScreen()
    }
}
