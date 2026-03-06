package com.on.dialog.feature.mypage.impl.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogDivider
import com.on.dialog.designsystem.component.DividerOrientation
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.impl.BuildKonfig
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.account_management
import dialog.feature.mypage.impl.generated.resources.delete_account
import dialog.feature.mypage.impl.generated.resources.logout
import dialog.feature.mypage.impl.generated.resources.privacy_policy
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AccountManagementSection(
    modifier: Modifier = Modifier,
    onLogoutClick: (() -> Unit)? = null,
    onDeleteAccount: (() -> Unit)? = null,
) {
    val uriHandler = LocalUriHandler.current

    DialogCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = DialogTheme.spacing.small)) {
            Text(
                text = stringResource(resource = Res.string.account_management),
                style = DialogTheme.typography.titleSmall,
                color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
            DialogDivider(
                orientation = DividerOrientation.Horizontal,
                modifier = Modifier.padding(vertical = DialogTheme.spacing.extraSmall),
            )
            MyPageMenuButton(
                text = stringResource(resource = Res.string.privacy_policy),
                onClick = { uriHandler.openUri(uri = BuildKonfig.PRIVACY_POLICY_URL) },
            ) {
                Icon(
                    imageVector = DialogIcons.Info,
                    contentDescription = stringResource(resource = Res.string.privacy_policy),
                )
            }
            onLogoutClick?.let {
                MyPageMenuButton(
                    text = stringResource(resource = Res.string.logout),
                    onClick = onLogoutClick,
                ) {
                    Icon(
                        imageVector = DialogIcons.Logout,
                        contentDescription = stringResource(resource = Res.string.logout),
                    )
                }
            }
            onDeleteAccount?.let {
                MyPageMenuButton(
                    text = stringResource(resource = Res.string.delete_account),
                    onClick = onDeleteAccount,
                ) {
                    Icon(
                        imageVector = DialogIcons.PersonOff,
                        contentDescription = stringResource(Res.string.delete_account),
                    )
                }
            }
        }
    }
}

@ThemePreview
@Composable
private fun AccountManagementSectionLoggedInPreview() {
    DialogTheme {
        Surface {
            AccountManagementSection(
                onLogoutClick = {},
                onDeleteAccount = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun AccountManagementSectionLoggedOutPreview() {
    DialogTheme {
        Surface {
            AccountManagementSection()
        }
    }
}
