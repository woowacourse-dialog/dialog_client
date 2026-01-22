package com.on.dialog

import androidx.navigation3.runtime.NavKey
import com.on.dialog.designsystem.component.NavigationItem
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.feature.main.api.MainNavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

object TopLevel {
    val routesKey: Set<NavKey> = setOf(MainNavKey, ScrapNavKey, MyPageNavKey)
    val routesNavigationItem: ImmutableList<NavigationItem> = persistentListOf(
        NavigationItem(icon = DialogIcons.Home, label = "홈"),
        NavigationItem(icon = DialogIcons.Bookmark, label = "북마크"),
        NavigationItem(icon = DialogIcons.Person, label = "마이페이지"),
    )
}
