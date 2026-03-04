package com.on.dialog.feature.mypage.impl.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogDivider
import com.on.dialog.designsystem.component.DividerOrientation
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.my_discussions
import dialog.feature.mypage.impl.generated.resources.my_favorites
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionManagementSection(
    onMyCreatedClick: () -> Unit,
    onMyFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = DialogTheme.spacing.small)) {
            Text(
                text = "토론 관리",
                style = DialogTheme.typography.titleSmall,
                color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
            DialogDivider(
                orientation = DividerOrientation.Horizontal,
                modifier = Modifier.padding(vertical = DialogTheme.spacing.extraSmall),
            )
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_discussions),
                onClick = onMyCreatedClick,
            ) { Icon(imageVector = DialogIcons.Forum, contentDescription = "") }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_favorites),
                onClick = onMyFavoriteClick,
            ) { Icon(imageVector = DialogIcons.Favorite, contentDescription = "") }
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionManagementSectionPreview() {
    DialogTheme {
        Surface {
            DiscussionManagementSection(
                onMyCreatedClick = {},
                onMyFavoriteClick = {},
            )
        }
    }
}
