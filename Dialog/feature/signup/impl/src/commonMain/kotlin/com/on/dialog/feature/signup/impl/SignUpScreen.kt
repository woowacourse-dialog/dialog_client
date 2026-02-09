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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.signup.impl.mapper.toFullNameRes
import com.on.dialog.feature.signup.impl.viewmodel.SignUpEffect
import com.on.dialog.feature.signup.impl.viewmodel.SignUpIntent
import com.on.dialog.feature.signup.impl.viewmodel.SignUpState
import com.on.dialog.feature.signup.impl.viewmodel.SignUpViewModel
import com.on.dialog.model.common.Track
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.notification_confirm
import dialog.feature.signup.impl.generated.resources.signup
import dialog.feature.signup.impl.generated.resources.track
import dialog.feature.signup.impl.generated.resources.track_placeholder
import dialog.feature.signup.impl.generated.resources.track_supporting_text
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current
    val uiState: SignUpState by viewModel.uiState.collectAsStateWithLifecycle()

    val tracks: ImmutableList<String> =
        Track.entries
            .filter { it != Track.COMMON }
            .map { stringResource(it.toFullNameRes()) }
            .toImmutableList()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect: SignUpEffect ->
            when (effect) {
                SignUpEffect.NavigateHome -> navigateToHome()

                is SignUpEffect.ShowSnackbar -> snackbarHostState.showSnackbar(
                    message = getString(effect.stringResource),
                    state = effect.state,
                )
            }
        }
    }

    SignUpScreen(
        uiState = uiState,
        tracks = tracks,
        onSelectTrack = { viewModel.onIntent(SignUpIntent.SelectTrack(index = it)) },
        onToggleNotification = { viewModel.onIntent(SignUpIntent.ToggleNotification(enabled = it)) },
        onSignUpClick = { viewModel.onIntent(SignUpIntent.ValidateAndSignUp) },
        modifier = modifier,
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpState,
    tracks: ImmutableList<String>,
    onSelectTrack: (Int) -> Unit,
    onToggleNotification: (Boolean) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(title = stringResource(Res.string.signup))
        SignUpScreenContent(
            uiState = uiState,
            tracks = tracks,
            onSelectTrack = onSelectTrack,
            onToggleNotification = onToggleNotification,
            onSignUpClick = onSignUpClick,
        )
    }
}

@Composable
private fun SignUpScreenContent(
    uiState: SignUpState,
    tracks: ImmutableList<String>,
    onSelectTrack: (Int) -> Unit,
    onToggleNotification: (Boolean) -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(DialogTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DialogDropdownMenu(
            options = tracks,
            onSelectedIndexChange = onSelectTrack,
            label = stringResource(Res.string.track),
            placeholder = stringResource(Res.string.track_placeholder),
            selectedIndex = uiState.selectedTrackIndex,
            isError = uiState.isTrackUnSelected,
            supportingText = stringResource(Res.string.track_supporting_text),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = uiState.isNotificationEnabled,
                onCheckedChange = onToggleNotification,
            )
            Text(
                text = stringResource(Res.string.notification_confirm),
                style = DialogTheme.typography.bodyMedium,
            )
        }

        DialogButton(
            text = stringResource(Res.string.signup),
            onClick = onSignUpClick,
        )
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DialogTheme {
        Surface {
            SignUpScreen(
                uiState = SignUpState(),
                tracks = persistentListOf("안드로이드", "백엔드", "프론트엔드"),
                onSelectTrack = {},
                onToggleNotification = {},
                onSignUpClick = {},
            )
        }
    }
}
