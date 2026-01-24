package com.on.dialog.discussiondetail.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.discussiondetail.impl.DiscussionDetailScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.discussionDetailScreen(
    navigator: Navigator,
) {
    entry<DiscussionDetailNavKey> {
        DiscussionDetailScreen(goBack = { navigator.goBack() })
    }
}
