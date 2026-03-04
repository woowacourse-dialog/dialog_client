package com.on.dialog.feature.mypage.impl.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.feature.mypage.impl.model.UserInfoUiModel
import com.on.dialog.feature.mypage.impl.viewmodel.MyPageState
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.ProfileImage
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.empty_profile_login_required
import dialog.feature.mypage.impl.generated.resources.no_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileSection(
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
internal fun EmptyProfileSection(modifier: Modifier = Modifier) {
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
                Image(
                    painter = painterResource(Res.drawable.no_profile),
                    contentDescription = "이미지 없음",
                    modifier = Modifier
                        .size(size = 60.dp)
                        .border(
                            width = 0.5.dp,
                            color = DialogTheme.colorScheme.onSurface,
                            shape = CircleShape,
                        ).clip(CircleShape),
                )
                Text(
                    text = stringResource(Res.string.empty_profile_login_required),
                    style = DialogTheme.typography.titleMedium,
                )
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

@ThemePreview
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

@ThemePreview
@Composable
private fun EmptyProfileSectionPreview() {
    DialogTheme {
        Surface {
            EmptyProfileSection()
        }
    }
}
