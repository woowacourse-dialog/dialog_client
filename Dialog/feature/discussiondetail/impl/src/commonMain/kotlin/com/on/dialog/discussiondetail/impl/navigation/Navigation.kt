package com.on.dialog.discussiondetail.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.discussiondetail.impl.DiscussionDetailScreen
import com.on.dialog.feature.creatediscussion.api.EditDiscussionNavKey
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.discussionDetailScreen(
    navigator: Navigator,
) {
    entry<DiscussionDetailNavKey> {
        DiscussionDetailScreen(
            discussionId = it.discussionId,
            goBack = { navigator.goBack() },
            navigateToEdit = { discussionId: Long ->
                navigator.navigate(EditDiscussionNavKey(discussionId = discussionId))
            },
        )
    }
}
