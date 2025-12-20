package com.on.dialog.designsystem.component

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.ui.tooling.preview.Preview

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
)

@Composable
fun DialogNavigationBar(
    items: List<NavigationItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            val iconColor = if (isSelected) MaterialTheme.colorScheme.primary else Gray400
            val textColor = if (isSelected) MaterialTheme.colorScheme.primary else Gray400

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
                            style = MaterialTheme.typography.labelLarge,
                            color = textColor,
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Gray400,
                ),
                interactionSource = remember { NoRippleInteractionSource },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogNavigationBarPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    DialogTheme {
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
}

private object NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}
