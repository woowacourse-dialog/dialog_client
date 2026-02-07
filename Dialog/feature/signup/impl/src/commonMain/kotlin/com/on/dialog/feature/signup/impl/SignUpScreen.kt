package com.on.dialog.feature.signup.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.signup.impl.mapper.toFullNameRes
import com.on.dialog.feature.signup.impl.viewmodel.SignUpEffect
import com.on.dialog.feature.signup.impl.viewmodel.SignUpViewModel
import com.on.dialog.model.common.Track
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.notification_confirm
import dialog.feature.signup.impl.generated.resources.signup
import dialog.feature.signup.impl.generated.resources.track
import dialog.feature.signup.impl.generated.resources.track_placeholder
import dialog.feature.signup.impl.generated.resources.track_supporting_text
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect: SignUpEffect ->
            when (effect) {
                SignUpEffect.NavigateHome -> navigateToHome()

                is SignUpEffect.ShowSnackbar -> snackbarHostState.showSnackbar(
                    message = effect.message,
                    state = effect.state,
                )
            }
        }
    }

    SignUpScreen(onSignUp = viewModel::signup, modifier = modifier)
}

@Composable
private fun SignUpScreen(
    onSignUp: (track: Track, isNotificationEnabled: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(title = stringResource(Res.string.signup))
        SignUpScreenContent(onSignUp = onSignUp)
    }
}

@Composable
private fun SignUpScreenContent(
    onSignUp: (track: Track, isNotificationEnabled: Boolean) -> Unit,
) {
    val tracks: ImmutableList<String> =
        Track.entries
            .filter { it != Track.COMMON }
            .map { stringResource(it.toFullNameRes()) }
            .toImmutableList()

    var isTrackSelected by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var selectedIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var notificationEnabled by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(DialogTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DialogDropdownMenu(
            options = tracks,
            onSelectedIndexChange = {
                selectedIndex = it
                isTrackSelected = true
            },
            label = stringResource(Res.string.track),
            placeholder = stringResource(Res.string.track_placeholder),
            selectedIndex = selectedIndex,
            isError = isTrackSelected == false,
            supportingText = stringResource(Res.string.track_supporting_text),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = notificationEnabled,
                onCheckedChange = { notificationEnabled = it },
            )
            Text(
                text = stringResource(Res.string.notification_confirm),
                style = DialogTheme.typography.bodyMedium,
            )
        }

        DialogButton(
            text = stringResource(Res.string.signup),
            onClick = {
                if (isTrackSelected != true) {
                    isTrackSelected = false
                } else {
                    onSignUp(Track.entries[selectedIndex ?: 0], notificationEnabled)
                }
            },
        )
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DialogTheme {
        Surface {
            SignUpScreen(onSignUp = { _, _ -> })
        }
    }
}
