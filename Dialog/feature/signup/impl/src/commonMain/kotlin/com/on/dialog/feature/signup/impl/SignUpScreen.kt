package com.on.dialog.feature.signup.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.signup.impl.mapper.toFullNameRes
import com.on.dialog.model.common.Track
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.track
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
) {
    val tracks: ImmutableList<String> =
        Track.entries.filter { it != Track.COMMON }.map { stringResource(it.toFullNameRes()) }
            .toImmutableList()

    var selectedIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var notificationEnabled by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(title = stringResource(Res.string.signup))

        Column(
            modifier = Modifier.padding(DialogTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DialogDropdownMenu(
                options = tracks,
                onSelectedIndexChange = { selectedIndex = it },
                label = stringResource(Res.string.track),
                placeholder = "트랙을 선택해주세요",
                selectedIndex = selectedIndex,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = notificationEnabled, onCheckedChange = { notificationEnabled = it })
                Text(text = "푸시 알림 수신 동의", style = DialogTheme.typography.bodyMedium)
            }

            DialogButton(
                text = stringResource(Res.string.signup),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DialogTheme {
        Surface {
            SignUpScreen()
        }
    }
}
