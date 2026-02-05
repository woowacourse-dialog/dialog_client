package com.on.dialog.feature.mypage.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.impl.mapper.message
import com.on.dialog.feature.mypage.impl.mapper.toFullNameRes
import com.on.dialog.feature.mypage.impl.model.TrackUiModel
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.NicknameState
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.cancel
import dialog.feature.mypage.impl.generated.resources.nickname
import dialog.feature.mypage.impl.generated.resources.nickname_placeholder
import dialog.feature.mypage.impl.generated.resources.save
import dialog.feature.mypage.impl.generated.resources.track
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditDialog(
    nickname: String,
    track: TrackUiModel,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier
            .background(color = DialogTheme.colorScheme.surface, shape = DialogTheme.shapes.medium)
            .padding(all = 20.dp),
        onDismissRequest = onDismissRequest,
    ) {
        EditDialogContent(
            nickname = nickname,
            track = track,
            onDismissRequest = onDismissRequest,
            onUpdateProfile = onUpdateProfile,
        )
    }
}

@Composable
private fun EditDialogContent(
    nickname: String,
    track: TrackUiModel,
    onDismissRequest: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
) {
    val tracks: ImmutableList<String> =
        Track.entries
            .filter { it != Track.COMMON }
            .map { stringResource(it.toFullNameRes()) }
            .toImmutableList()
    val selectedTrackFullName = stringResource(track.fullNameRes)

    var nickname: String by rememberSaveable { mutableStateOf(nickname) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(tracks.indexOf(selectedTrackFullName)) }
    val nicknameState: NicknameState by derivedStateOf { NicknameState.from(nickname = nickname) }

    Column {
        DialogTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = stringResource(Res.string.nickname),
            placeholder = stringResource(Res.string.nickname_placeholder),
            supportingText = nicknameState.message(),
            isError = nicknameState !is NicknameState.Valid,
        )

        DialogDropdownMenu(
            options = tracks,
            onSelectedIndexChange = { selectedIndex = it },
            label = stringResource(Res.string.track),
            selectedIndex = selectedIndex,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraExtraLarge))

        Row(horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium)) {
            DialogButton(
                text = stringResource(Res.string.cancel),
                style = DialogButtonStyle.Secondary,
                onClick = onDismissRequest,
                modifier = Modifier.weight(1f),
            )
            DialogButton(
                text = stringResource(Res.string.save),
                onClick = {
                    if (nicknameState is NicknameState.Valid) {
                        onUpdateProfile(nickname, Track.entries[selectedIndex])
                        onDismissRequest()
                    }
                },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditDialogPreview() {
    DialogTheme {
        Surface {
            ProfileEditDialog(
                nickname = "크림",
                track = Track.ANDROID.toUiModel(),
                onDismissRequest = {},
                onUpdateProfile = { _, _ -> },
            )
        }
    }
}
