package com.on.dialog.feature.mypage.impl

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.feature.mypage.impl.model.UserInfoUiModel
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageEffect
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageIntent
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageState
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.ProfileImage
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.error_imagepicker
import dialog.feature.mypage.impl.generated.resources.login
import dialog.feature.mypage.impl.generated.resources.logout
import dialog.feature.mypage.impl.generated.resources.my_discussions
import dialog.feature.mypage.impl.generated.resources.my_scraps
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
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current
    val uiState: MyPageState by viewModel.uiState.collectAsStateWithLifecycle()
    var showGallery by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(intent = MyPageIntent.CheckLoginStatus)

        viewModel.effect.collect { effect: MyPageEffect ->
            if (effect is MyPageEffect.ShowSnackbar) {
                snackbarHostState.showSnackbar(message = effect.message, state = effect.state)
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
            )
        } else {
            MyPageScreenLoggedOut(onLoginClick = onLoginClick)
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
) {
    var showProfileEditDialog by rememberSaveable { mutableStateOf(false) }

    ProfileSection(
        uiState = uiState,
        onEditClick = { showProfileEditDialog = true },
        onProfileImageClick = onProfileImageClick,
    )
    Spacer(Modifier.height(height = DialogTheme.spacing.extraLarge))
    DialogCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_discussions),
                onClick = onMyCreatedClick,
            ) { Icon(imageVector = DialogIcons.Forum, contentDescription = "") }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.my_scraps),
                onClick = {},
            ) { Icon(imageVector = DialogIcons.Bookmark, contentDescription = "") }
            MyPageMenuButton(
                text = stringResource(resource = Res.string.logout),
                onClick = onLogoutClick,
            ) {
                Icon(
                    imageVector = DialogIcons.Logout,
                    contentDescription = stringResource(resource = Res.string.logout),
                )
            }
            //  TODO 임시로 만든 회원 탈퇴 버튼, 위치 수정 고려
            MyPageMenuButton(
                text = "회원 탈퇴",
                onClick = onDeleteAccount,
            ) {
                Icon(
                    imageVector = DialogIcons.Logout,
                    contentDescription = "회원 탈퇴",
                )
            }
        }
    }

    if (showProfileEditDialog) {
        ProfileEditDialog(
            nickname = uiState.userInfo.nickname,
            selectedTrack = uiState.userInfo.track,
            onDismissRequest = { showProfileEditDialog = false },
            onUpdateProfile = onUpdateProfile,
        )
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
    onEditClick: () -> Unit,
    onProfileImageClick: () -> Unit,
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
                    onClick = onProfileImageClick,
                )
                ProfileInfo(userInfo = uiState.userInfo)
            }
            DialogIconButton(onClick = onEditClick) {
                Icon(imageVector = DialogIcons.Edit, contentDescription = "")
            }
        }
    }
}

@Composable
private fun ProfileInfo(
    userInfo: UserInfoUiModel,
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
                text = userInfo.nickname,
                style = DialogTheme.typography.titleMedium,
            )
            Text(
                text = userInfo.track.initial,
                style = DialogTheme.typography.labelLarge,
            )
        }
        Text(text = userInfo.githubId, style = DialogTheme.typography.bodyMedium)
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
                    isLoggedIn = true,
                    userInfo = UserInfoUiModel(
                        nickname = "크림",
                        track = Track.ANDROID.toUiModel(),
                        githubId = "ijh1298",
                    ),
                ),
                onEditClick = {},
                onProfileImageClick = {},
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
                onUpdateProfile = { _, _ -> },
                onProfileImageClick = {},
                onDeleteAccount = {},
                onMyCreatedClick = {},
            )
        }
    }
}
