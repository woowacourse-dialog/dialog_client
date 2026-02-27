package com.on.dialog.scrap.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import com.on.dialog.navigation.Navigator
import com.on.dialog.scrap.impl.ScrapScreen

fun EntryProviderScope<NavKey>.scrapScreen(
    navigator: Navigator,
) {
    entry<ScrapNavKey> {
        ScrapScreen(
            navigateToDetail = { discussionId ->
                navigator.navigate(DiscussionDetailNavKey(discussionId = discussionId))
            },
            navigateToLogin = {
                navigator.navigate(MyPageNavKey)
            },
        )
    }
}
