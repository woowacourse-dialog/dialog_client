package com.on.dialog.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.api.DiscussionDetailNavKey
import com.on.dialog.impl.DiscussionDetailScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.discussionDetailScreen(
    navigator: Navigator,
) {
    entry<DiscussionDetailNavKey> {
        DiscussionDetailScreen(goBack = { navigator.goBack() })
    }
}
