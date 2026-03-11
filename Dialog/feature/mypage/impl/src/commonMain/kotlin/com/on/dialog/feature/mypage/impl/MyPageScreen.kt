package com.on.dialog.feature.mypage.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.impl.component.AccountManagementSection
import com.on.dialog.feature.mypage.impl.component.ConfirmDialog
import com.on.dialog.feature.mypage.impl.component.DiscussionManagementSection
import com.on.dialog.feature.mypage.impl.component.EmptyProfileSection
import com.on.dialog.feature.mypage.impl.component.ProfileEditDialog
import com.on.dialog.feature.mypage.impl.component.ProfileSection
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.feature.mypage.impl.model.UserInfoUiModel
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageEffect
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageIntent
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageState
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.state.LocalAppLoginState
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.delete_account
import dialog.feature.mypage.impl.generated.resources.delete_account_confirm
import dialog.feature.mypage.impl.generated.resources.delete_account_confirm_message
import dialog.feature.mypage.impl.generated.resources.error_imagepicker
import dialog.feature.mypage.impl.generated.resources.login
import dialog.feature.mypage.impl.generated.resources.logout
import dialog.feature.mypage.impl.generated.resources.logout_confirm_message
import io.github.aakira.napier.Napier
import io.github.ismoy.imagepickerkmp.domain.config.CameraCaptureConfig
import io.github.ismoy.imagepickerkmp.domain.models.CompressionLevel
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyPageScreen(
    navigateToLogin: () -> Unit,
    navigateToMyCreated: () -> Unit,
    navigateToScrap: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current
    val appLoginState = LocalAppLoginState.current
    val uiState: MyPageState by viewModel.uiState.collectAsStateWithLifecycle()
    var showGallery by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(intent = MyPageIntent.CheckLoginStatus)

        viewModel.effect.collect { effect: MyPageEffect ->
            when(effect) {
                is MyPageEffect.ObserveLoginStatus -> {
                    appLoginState.setLoggedIn(isLoggedIn = effect.isLoggedIn)
                }
                is MyPageEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = effect.message, state = effect.state)
                }
            }
        }
    }

    MyPageScreen(
        uiState = uiState,
        onLoginClick = navigateToLogin,
        onLogoutClick = { viewModel.onIntent(intent = MyPageIntent.Logout) },
        onUpdateProfile = { nickname: String, track: Track ->
            viewModel.onIntent(
                intent = MyPageIntent.EditProfile(nickname = nickname, track = track),
            )
        },
        onProfileImageClick = { showGallery = true },
        onDeleteAccount = { viewModel.onIntent(intent = MyPageIntent.DeleteAccount) },
        onMyCreatedClick = navigateToMyCreated,
        onScrapClick = navigateToScrap,
        onLoggedOutInteraction = {
            snackbarHostState.showSnackbar(
                message = "먼저 로그인을 해주세요",
                state = SnackbarState.DEFAULT,
                actionLabel = "로그인",
                onAction = navigateToLogin,
            )
        },
        modifier = modifier,
    )

    val errorMessage = stringResource(resource = Res.string.error_imagepicker)
    if (showGallery) {
        GalleryPickerLauncher(
            onPhotosSelected = { photos: List<GalleryPhotoResult> ->
                showGallery = false
                photos.firstOrNull()?.let { image: GalleryPhotoResult ->
                    Napier.d("selectedImage: $image")
                    viewModel.onIntent(intent = MyPageIntent.EditProfileImage(uri = image.uri))
                }
            },
            onError = {
                showGallery = false
                snackbarHostState.showSnackbar(
                    message = errorMessage,
                    state = SnackbarState.NEGATIVE,
                )
            },
            onDismiss = { showGallery = false },
            allowMultiple = false,
            selectionLimit = 1,
            cameraCaptureConfig = CameraCaptureConfig(compressionLevel = CompressionLevel.HIGH),
        )
    }
}

@Composable
private fun MyPageScreen(
    uiState: MyPageState,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
    onProfileImageClick: () -> Unit,
    onDeleteAccount: () -> Unit,
    onMyCreatedClick: () -> Unit,
    onScrapClick: () -> Unit,
    onLoggedOutInteraction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isLoggedIn) {
            MyPageScreenLoggedIn(
                uiState = uiState,
                onLogoutClick = onLogoutClick,
                onUpdateProfile = onUpdateProfile,
                onProfileImageClick = onProfileImageClick,
                onDeleteAccount = onDeleteAccount,
                onMyCreatedClick = onMyCreatedClick,
                onScrapClick = onScrapClick,
            )
        } else {
            MyPageScreenLoggedOut(
                onLoginClick = onLoginClick,
                onLoggedOutInteraction = onLoggedOutInteraction
            )
        }
    }
}

@Composable
private fun MyPageScreenLoggedIn(
    uiState: MyPageState,
    onLogoutClick: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
    onProfileImageClick: () -> Unit,
    onDeleteAccount: () -> Unit,
    onMyCreatedClick: () -> Unit,
    onScrapClick: () -> Unit,
) {
    var showProfileEditDialog by rememberSaveable { mutableStateOf(false) }
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteAccountDialog by rememberSaveable { mutableStateOf(false) }

    ProfileSection(
        uiState = uiState,
        onEditClick = { showProfileEditDialog = true },
        onProfileImageClick = onProfileImageClick,
    )
    Spacer(Modifier.height(height = DialogTheme.spacing.extraLarge))

    DiscussionManagementSection(
        onMyCreatedClick = onMyCreatedClick,
        onScrapClick = onScrapClick,
    )
    Spacer(Modifier.height(height = DialogTheme.spacing.large))
    AccountManagementSection(
        onLogoutClick = { showLogoutDialog = true },
        onDeleteAccount = { showDeleteAccountDialog = true },
    )

    if (showProfileEditDialog) {
        ProfileEditDialog(
            nickname = uiState.userInfo.nickname,
            selectedTrack = uiState.userInfo.track,
            onDismissRequest = { showProfileEditDialog = false },
            onUpdateProfile = onUpdateProfile,
        )
    }

    if (showLogoutDialog) {
        ConfirmDialog(
            title = stringResource(resource = Res.string.logout),
            message = stringResource(resource = Res.string.logout_confirm_message),
            confirmText = stringResource(resource = Res.string.logout),
            onDismissRequest = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                onLogoutClick()
            },
        )
    }

    if (showDeleteAccountDialog) {
        ConfirmDialog(
            title = stringResource(resource = Res.string.delete_account),
            message = stringResource(resource = Res.string.delete_account_confirm_message),
            confirmText = stringResource(resource = Res.string.delete_account_confirm),
            onDismissRequest = { showDeleteAccountDialog = false },
            onConfirm = {
                showDeleteAccountDialog = false
                onDeleteAccount()
            },
            confirmButtonStyle = DialogButtonStyle.Error,
        )
    }
}

@Composable
private fun MyPageScreenLoggedOut(
    onLoginClick: () -> Unit,
    onLoggedOutInteraction: () -> Unit,
) {
    EmptyProfileSection()
    Spacer(modifier = Modifier.height(height = DialogTheme.spacing.extraLarge))
    LoginButton(onLoginClick = onLoginClick)
    Spacer(modifier = Modifier.height(height = DialogTheme.spacing.extraLarge))
    DiscussionManagementSection(
        onMyCreatedClick = { onLoggedOutInteraction() },
        onScrapClick = { onLoggedOutInteraction() },
    )
    Spacer(modifier = Modifier.height(height = DialogTheme.spacing.large))
    AccountManagementSection()
}

@Composable
private fun LoginButton(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogCard(
        modifier = modifier.fillMaxWidth(),
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

@Preview
@Composable
private fun MyPageScreenLoggedInPreview() {
    DialogTheme {
        Surface {
            MyPageScreen(
                uiState = MyPageState(
                    imageUrl = "",
                    isLoggedIn = true,
                    userInfo = UserInfoUiModel(
                        nickname = "크림",
                        track = Track.ANDROID.toUiModel(),
                        githubId = "ijh1298",
                    ),
                ),
                onLoginClick = {},
                onLogoutClick = {},
                onUpdateProfile = { _, _ -> },
                onProfileImageClick = {},
                onDeleteAccount = {},
                onMyCreatedClick = {},
                onScrapClick = {},
                onLoggedOutInteraction = {},
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
                    imageUrl = "",
                    isLoggedIn = false,
                    userInfo = UserInfoUiModel(
                        nickname = "",
                        track = Track.ANDROID.toUiModel(),
                        githubId = "",
                    ),
                ),
                onLoginClick = {},
                onLogoutClick = {},
                onUpdateProfile = { _, _ -> },
                onProfileImageClick = {},
                onDeleteAccount = {},
                onMyCreatedClick = {},
                onScrapClick = {},
                onLoggedOutInteraction = {},
            )
        }
    }
}
