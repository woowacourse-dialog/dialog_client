package com.on.dialog.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.source.NoRippleInteractionSource
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import dialog.feature.main.generated.resources.Res
import dialog.feature.main.generated.resources.top_level_nav_item_home
import dialog.feature.main.generated.resources.top_level_nav_item_my_page
import dialog.feature.main.generated.resources.top_level_nav_item_scrap
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class NavigationItem(
    val label: StringResource,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
)

/**
 * 다이얼로그 디자인 시스템의 하단 내비게이션 바.
 * 화면 하단에 메뉴 목록을 표시하고, 선택된 메뉴를 강조합니다.
 *
 * @param items 표시할 [NavigationItem] 리스트. 각 아이템은 라벨, 기본 아이콘, 선택 아이콘으로 구성됩니다.
 * @param selectedIndex 현재 선택된 아이템의 인덱스.
 * @param onSelectedIndexChange 아이템을 클릭했을 때 호출될 콜백. 클릭된 아이템의 인덱스를 전달합니다.
 * @param modifier 내비게이션 바에 적용할 [Modifier].
 */
@Composable
fun DialogNavigationBar(
    items: Map<NavKey, NavigationItem>,
    selectedKey: NavKey,
    onSelectedKeyChange: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = DialogTheme.colorScheme.surface,
        contentColor = DialogTheme.colorScheme.onSurface,
        modifier = modifier,
    ) {
        items.forEach { (navKey, item) ->
            val isSelected = selectedKey == navKey
            val primaryColor = DialogTheme.colorScheme.primary
            val iconColor = if (isSelected) primaryColor else primaryColor.copy(alpha = 0.3f)
            val textColor = if (isSelected) primaryColor else primaryColor.copy(alpha = 0.3f)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectedKeyChange(navKey) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = stringResource(resource = item.label),
                            tint = iconColor,
                            modifier = Modifier.size(32.dp),
                        )
                        Text(
                            text = stringResource(resource = item.label),
                            style = DialogTheme.typography.labelLarge,
                            color = textColor,
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = DialogTheme.colorScheme.primary,
                    unselectedIconColor = DialogTheme.colors.gray400
                ),
                interactionSource = NoRippleInteractionSource,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogNavigationBarPreviewLight() {
    DialogTheme {
        DialogNavigationBarPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogNavigationBarPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogNavigationBarPreviewContent()
    }
}

@Composable
private fun DialogNavigationBarPreviewContent() {
    var selectedIndex by remember { mutableStateOf(DiscussionListNavKey) }
    DialogNavigationBar(
        items = mapOf(
            DiscussionListNavKey to NavigationItem(
                icon = DialogIcons.Home,
                label = Res.string.top_level_nav_item_home,
            ),
            ScrapNavKey to NavigationItem(
                icon = DialogIcons.Bookmark,
                label = Res.string.top_level_nav_item_scrap,
            ),
            MyPageNavKey to NavigationItem(
                icon = DialogIcons.Person,
                label = Res.string.top_level_nav_item_my_page,
            ),
        ),
        selectedKey = selectedIndex,
        onSelectedKeyChange = { },
    )
}
