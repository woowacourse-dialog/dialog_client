package com.on.dialog

import androidx.navigation3.runtime.NavKey
import com.on.dialog.designsystem.component.NavigationItem
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.feature.main.api.MainNavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey

object TopLevel {
    val routes: Map<NavKey, NavigationItem> = mapOf(
        MainNavKey to NavigationItem(icon = DialogIcons.Home, label = "홈"),
        ScrapNavKey to NavigationItem(icon = DialogIcons.Bookmark, label = "북마크"),
        MyPageNavKey to NavigationItem(icon = DialogIcons.Person, label = "마이페이지"),
    )
}
