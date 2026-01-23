package com.on.main

import androidx.navigation3.runtime.NavKey
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import com.on.main.component.NavigationItem
import dialog.feature.main.generated.resources.Res
import dialog.feature.main.generated.resources.top_level_nav_item_home
import dialog.feature.main.generated.resources.top_level_nav_item_my_page
import dialog.feature.main.generated.resources.top_level_nav_item_scrap

object TopLevel {
    val routes: Map<NavKey, NavigationItem> = mapOf(
        DiscussionListNavKey to NavigationItem(
            icon = DialogIcons.Home,
            label = Res.string.top_level_nav_item_home
        ),
        ScrapNavKey to NavigationItem(
            icon = DialogIcons.Bookmark,
            label = Res.string.top_level_nav_item_scrap
        ),
        MyPageNavKey to NavigationItem(
            icon = DialogIcons.Person,
            label = Res.string.top_level_nav_item_my_page
        ),
    )
}
