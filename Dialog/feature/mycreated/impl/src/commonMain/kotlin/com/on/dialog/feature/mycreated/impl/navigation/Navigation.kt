package com.on.dialog.feature.mycreated.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.discussiondetail.api.DiscussionDetailNavKey
import com.on.dialog.feature.mycreated.api.MyCreatedNavKey
import com.on.dialog.feature.mycreated.impl.MyCreatedScreen
import com.on.dialog.navigation.Navigator

fun EntryProviderScope<NavKey>.myCreatedScreen(
    navigator: Navigator,
) {
    entry<MyCreatedNavKey> {
        MyCreatedScreen(
            navigateToDetail = { discussionId ->
                navigator.navigate(DiscussionDetailNavKey(discussionId = discussionId))
            },
            navigateToCreateDiscussion = {
                // todo: 토론 생성 화면으로 이동
            },
            goBack = navigator::goBack,
        )
    }
}
