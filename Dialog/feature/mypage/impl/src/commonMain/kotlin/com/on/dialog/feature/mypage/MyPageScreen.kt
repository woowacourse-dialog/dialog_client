package com.on.dialog.feature.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.ProfileImage
import com.on.model.common.Track
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.login
import dialog.feature.mypage.impl.generated.resources.logout
import dialog.feature.mypage.impl.generated.resources.my_discussions
import dialog.feature.mypage.impl.generated.resources.my_scraps
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyPageScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = koinViewModel(),
) {
    val uiState: MyPageState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(intent = MyPageIntent.CheckLoginStatus)
    }

    MyPageScreen(
        uiState = uiState,
        onLoginClick = { navigateToLogin() },
        onLogoutClick = { viewModel.onIntent(intent = MyPageIntent.Logout) },
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageState,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isLoggedIn) {
            MyPageScreenLoggedIn(uiState = uiState, onLogoutClick = onLogoutClick)
        } else {
            MyPageScreenLoggedOut(onLoginClick = onLoginClick)
        }
    }
}

@Composable
private fun MyPageScreenLoggedIn(
    uiState: MyPageState,
    onLogoutClick: () -> Unit,
) {
    ProfileSection(uiState)
    Spacer(Modifier.height(height = DialogTheme.spacing.extraLarge))
    DialogCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_discussions),
                onClick = {},
            ) { Icon(imageVector = DialogIcons.Forum, contentDescription = "") }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_scraps),
                onClick = {},
            ) { Icon(imageVector = DialogIcons.Bookmark, contentDescription = "") }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.logout),
                onClick = onLogoutClick,
            ) { Icon(imageVector = DialogIcons.Logout, contentDescription = "") }
        }
    }
}

@Composable
private fun MyPageScreenLoggedOut(
    onLoginClick: () -> Unit,
) {
    DialogCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onLoginClick,
    ) {
        Row {
            Icon(
                imageVector = DialogIcons.Login,
                contentDescription = "",
                modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.width(width = ButtonDefaults.IconSpacing))
            Text(
                text = stringResource(resource = Res.string.login),
                style = DialogTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun MyPageMenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = DialogTheme.shapes.small)
            .clickable { onClick() }
            .padding(
                vertical = DialogTheme.spacing.medium,
                horizontal = DialogTheme.spacing.small,
            ),
    ) {
        leadingIcon?.let {
            Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
                leadingIcon()
            }
            Spacer(modifier = Modifier.width(width = ButtonDefaults.IconSpacing))
        }
        Text(text = text, style = DialogTheme.typography.labelLarge)
    }
}

@Composable
private fun ProfileSection(
    uiState: MyPageState,
    modifier: Modifier = Modifier,
) {
    DialogCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.medium),
            ) {
                ProfileImage(
                    imageUrl = uiState.imageUrl,
                    contentDescription = "",
                    modifier = Modifier.size(size = 60.dp),
                )
                ProfileInfo(
                    nickname = uiState.nickname,
                    track = uiState.track,
                    githubId = uiState.githubId,
                )
            }
            DialogIconButton(onClick = {}) {
                Icon(imageVector = DialogIcons.Edit, contentDescription = "")
            }
        }
    }
}

@Composable
private fun ProfileInfo(
    nickname: String,
    track: String,
    githubId: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.extraSmall),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.extraSmall),
        ) {
            Text(
                text = nickname,
                style = DialogTheme.typography.titleMedium,
            )
            Text(
                text = track,
                style = DialogTheme.typography.labelLarge,
            )
        }
        Text(text = githubId, style = DialogTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun ProfileSectionPreview() {
    DialogTheme {
        Surface {
            ProfileSection(
                uiState = MyPageState(
                    imageUrl = "",
                    nickname = "크림",
                    track = Track.ANDROID.initial,
                    githubId = "ijh1298",
                    isNotificationEnable = false,
                    isLoggedIn = true,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun MyPageScreenLoggedInPreview() {
    DialogTheme {
        Surface {
            MyPageScreen(
                uiState = MyPageState(
                    imageUrl = "",
                    nickname = "크림",
                    track = Track.ANDROID.initial,
                    githubId = "ijh1298",
                    isNotificationEnable = false,
                    isLoggedIn = true,
                ),
                onLoginClick = {},
                onLogoutClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun MyPageScreenLoggedOutPreview() {
    DialogTheme {
        Surface {
            MyPageScreen(
                uiState = MyPageState(
                    isLoggedIn = false,
                ),
                onLoginClick = {},
                onLogoutClick = {},
            )
        }
    }
}
