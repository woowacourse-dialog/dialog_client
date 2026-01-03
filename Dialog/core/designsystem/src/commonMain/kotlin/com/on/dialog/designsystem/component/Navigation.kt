package com.on.dialog.designsystem.component

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
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.Gray400
import org.jetbrains.compose.ui.tooling.preview.Preview

data class NavigationItem(
    val label: String,
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
    items: List<NavigationItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = DialogTheme.colorScheme.surface,
        contentColor = DialogTheme.colorScheme.onSurface,
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            val primaryColor = DialogTheme.colorScheme.primary
            val iconColor = if (isSelected) primaryColor else primaryColor.copy(alpha = 0.3f)
            val textColor = if (isSelected) primaryColor else primaryColor.copy(alpha = 0.3f)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectedIndexChange(index) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = item.label,
                            tint = iconColor,
                            modifier = Modifier.size(32.dp),
                        )
                        Text(
                            text = item.label,
                            style = DialogTheme.typography.labelLarge,
                            color = textColor,
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = DialogTheme.colorScheme.primary,
                    unselectedIconColor = Gray400,
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
    var selectedIndex by remember { mutableStateOf(0) }
    DialogNavigationBar(
        items = listOf(
            NavigationItem(
                label = "홈",
                icon = DialogIcons.Home,
            ),
            NavigationItem(
                label = "검색",
                icon = DialogIcons.Search,
            ),
            NavigationItem(
                label = "스크랩",
                icon = DialogIcons.Bookmark,
            ),
            NavigationItem(
                label = "프로필",
                icon = DialogIcons.Person,
            ),
        ),
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
    )
}
