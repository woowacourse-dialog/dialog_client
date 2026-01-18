package com.on.dialog.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogIconButtonTone
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.login.LoginType
import com.on.dialog.feature.login.LoginWebViewScreen
import com.on.dialog.ui.component.ProfileImage
import com.on.model.common.Track
import dialog.feature.mypage.generated.resources.Res
import dialog.feature.mypage.generated.resources.login
import dialog.feature.mypage.generated.resources.logout
import dialog.feature.mypage.generated.resources.my_discussions
import dialog.feature.mypage.generated.resources.my_scraps
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.onIntent(MyPageUiIntent.LoadMyPage)
    }

    val uiState: MyPageUiState by viewModel.uiState.collectAsState()
    var showLoginWebView: Boolean by rememberSaveable { mutableStateOf(false) }

    when (showLoginWebView) {
        true -> {
            LoginWebViewScreen(
                loginType = LoginType.GITHUB,
                onLoginSuccess = {
                    showLoginWebView = false
                    viewModel.onIntent(MyPageUiIntent.LoadMyPage)
                },
                onLoginFailure = { showLoginWebView = false },
                onLoginCancel = { showLoginWebView = false },
                onSignUp = {
                    // TODO 트랙 선택 화면 이동
                },
            )
        }

        false -> {
            MyPageScreen(
                uiState = uiState,
                onLoginClick = { showLoginWebView = true },
                onLogoutClick = { viewModel.onIntent(MyPageUiIntent.Logout) },
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    isLoggedIn: Boolean = uiState.isLoggedIn,
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DialogTheme.colorScheme.surfaceContainer)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (isLoggedIn) {
            true -> MyPageScreenLoggedIn(uiState, onLogoutClick)
            false -> MyPageScreenLoggedOut(onLoginClick)
        }
    }
}

@Composable
private fun MyPageScreenLoggedIn(
    uiState: MyPageUiState,
    onLogoutClick: () -> Unit,
) {
    ProfileSection(uiState)
    Spacer(Modifier.height(DialogTheme.spacing.extraLarge))
    DialogCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            DialogButton(
                text = stringResource(Res.string.my_discussions),
                style = DialogButtonStyle.None,
                onClick = {},
            ) { Icon(imageVector = DialogIcons.Forum, contentDescription = "") }
            DialogButton(
                text = stringResource(Res.string.my_scraps),
                style = DialogButtonStyle.None,
                onClick = {},
            ) { Icon(imageVector = DialogIcons.Bookmark, contentDescription = "") }
            DialogButton(
                text = stringResource(Res.string.logout),
                style = DialogButtonStyle.None,
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
    ) {
        DialogButton(
            text = stringResource(Res.string.login),
            style = DialogButtonStyle.None,
            onClick = onLoginClick,
        ) { Icon(imageVector = DialogIcons.Login, contentDescription = "") }
    }
}

@Composable
fun ProfileSection(
    uiState: MyPageUiState,
    modifier: Modifier = Modifier,
) {
    DialogCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            ) {
                ProfileImage(
                    imageUrl = uiState.imageUrl,
                    contentDescription = "",
                    modifier = Modifier.size(60.dp),
                )
                ProfileInfo(
                    nickname = uiState.nickname,
                    track = uiState.track,
                    githubId = uiState.githubId,
                )
            }
            DialogIconButton(
                tone = DialogIconButtonTone.Primary,
                onClick = {},
            ) {
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
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
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
                MyPageUiState(
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
                uiState = MyPageUiState(
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
private fun MyPageScreenLoggedOutPreview() {
    DialogTheme {
        Surface {
            MyPageScreen(
                uiState = MyPageUiState(
                    isLoggedIn = false,
                ),
            )
        }
    }
}
