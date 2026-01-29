package com.on.dialog.feature.mypage

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
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.model.NicknameState
import com.on.dialog.ui.mapper.toStringResource
import com.on.model.common.Track
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditDialog(
    nickname: String,
    track: Track,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier
            .background(color = DialogTheme.colorScheme.surface)
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
    track: Track,
    onDismissRequest: () -> Unit,
    onUpdateProfile: (nickname: String, track: Track) -> Unit,
) {
    var nickname: String by rememberSaveable { mutableStateOf(nickname) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(track.ordinal) }
    val nicknameState: NicknameState by derivedStateOf { NicknameState.of(nickname = nickname) }

    Column {
        DialogTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = "닉네임",
            placeholder = "닉네임을 입력하세요",
            supportingText = nicknameState.message,
            isError = nicknameState != NicknameState.Valid,
        )

        DialogDropdownMenu(
            options = Track.entries.map { stringResource(it.toStringResource()) }.toImmutableList(),
            onSelectedIndexChange = { selectedIndex = it },
            label = "트랙",
            selectedIndex = selectedIndex,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraExtraLarge))

        Row(horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium)) {
            DialogButton(
                text = "취소",
                style = DialogButtonStyle.Secondary,
                onClick = onDismissRequest,
                modifier = Modifier.weight(1f),
            )
            DialogButton(
                text = "저장",
                onClick = {
                    if (nicknameState == NicknameState.Valid) {
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
                track = Track.ANDROID,
                onDismissRequest = {},
                onUpdateProfile = { _, _ -> },
            )
        }
    }
}
