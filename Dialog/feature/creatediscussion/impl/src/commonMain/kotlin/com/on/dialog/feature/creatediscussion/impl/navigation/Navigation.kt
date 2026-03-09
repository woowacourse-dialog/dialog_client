package com.on.dialog.feature.creatediscussion.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.feature.creatediscussion.api.CreateDiscussionNavKey
import com.on.dialog.feature.creatediscussion.impl.CreateDiscussionScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.createDiscussionScreen(
    navigator: Navigator,
) {
    entry<CreateDiscussionNavKey> {
        CreateDiscussionScreen(
            goBack = navigator::goBack,
            navigateToDetail = { discussionId: Long ->
                navigator.replace(DiscussionDetailNavKey(discussionId = discussionId))
            }
        )
    }
}
