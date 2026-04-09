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
import dialog.feature.mypage.impl.generated.resources.discussion_management
import dialog.feature.mypage.impl.generated.resources.my_discussions
import dialog.feature.mypage.impl.generated.resources.my_scraps
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionManagementSection(
    onMyCreatedClick: () -> Unit,
    onScrapClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogCard(modifier = modifier.fillMaxWidth()) {
        Column {
            Text(
                text = stringResource(resource = Res.string.discussion_management),
                style = DialogTheme.typography.titleSmall,
                color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = DialogTheme.spacing.medium),
            )
            DialogDivider(
                orientation = DividerOrientation.Horizontal,
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.medium,
                    vertical = DialogTheme.spacing.extraSmall,
                ),
            )
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_discussions),
                onClick = onMyCreatedClick,
            ) {
                Icon(
                    imageVector = DialogIcons.Forum,
                    contentDescription = stringResource(resource = Res.string.my_discussions),
                )
            }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_scraps),
                onClick = onScrapClick,
            ) {
                Icon(
                    imageVector = DialogIcons.Bookmark,
                    contentDescription = stringResource(resource = Res.string.my_scraps),
                )
            }
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
                onScrapClick = {},
            )
        }
    }
}
