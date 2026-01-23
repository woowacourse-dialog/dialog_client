package com.on.dialog.feature.discussionlist.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.feature.discussionlist.impl.DiscussionListScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.discussionListScreen(
    navigator: Navigator,
) {
    entry<DiscussionListNavKey> {
        DiscussionListScreen(navigateToDiscussionDetail = {
            navigator.navigate(
                DiscussionDetailNavKey,
            )
        })
    }
}
