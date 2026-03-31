package com.on.dialog.feature.login.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.core.common.Platform
import com.on.dialog.core.common.currentPlatform
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.login.impl.component.AppleLoginButton
import com.on.dialog.feature.login.impl.component.GithubLoginButton
import com.on.dialog.feature.login.impl.model.LoginError
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.viewmodel.LoginEffect
import com.on.dialog.feature.login.impl.viewmodel.LoginIntent
import com.on.dialog.feature.login.impl.viewmodel.LoginState
import com.on.dialog.feature.login.impl.viewmodel.LoginViewModel
import dialog.feature.login.impl.generated.resources.Res
import dialog.feature.login.impl.generated.resources.login_background
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    goBack: () -> Unit,
    navigateToSignUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current
    val uiState: LoginState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.GoBack -> goBack()

                is LoginEffect.NavigateToSignUp -> navigateToSignUp(effect.jsessionId)

                is LoginEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = getString(effect.stringResource),
                        state = effect.state,
                    )
                }
            }
        }
    }

    when (uiState.loginType) {
        LoginType.NONE -> {
            LoginSelectionScreen(
                onGithubLogin = { viewModel.onIntent(LoginIntent.SelectLoginType(LoginType.GITHUB)) },
                onAppleLogin = { viewModel.onIntent(LoginIntent.SelectLoginType(LoginType.APPLE)) },
                modifier = modifier,
            )
        }

        LoginType.GITHUB -> {
            LoginWebViewScreen(
                uiState = uiState,
                goBack = goBack,
                onLoginSuccess = { jsessionId, isNewUser ->
                    viewModel.onIntent(
                        LoginIntent.LoginSuccess(jsessionId = jsessionId, isNewUser = isNewUser),
                    )
                },
                onLoginFailure = { error: LoginError ->
                    viewModel.onIntent(LoginIntent.LoginFailure(loginError = error))
                    goBack()
                },
                modifier = modifier.fillMaxSize(),
            )
        }

        LoginType.APPLE -> Unit
    }
}

@Composable
private fun LoginSelectionScreen(
    onGithubLogin: () -> Unit,
    onAppleLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = DialogTheme.spacing.extraExtraLarge)
                .padding(bottom = 100.dp),
        ) {
            if (currentPlatform == Platform.IOS) {
                AppleLoginButton(
                    onClick = onAppleLogin,
                    modifier = Modifier.padding(bottom = DialogTheme.spacing.medium),
                )
            }
            GithubLoginButton(onClick = onGithubLogin)
        }
    }
}

@Preview
@Composable
private fun LoginSelectionScreenPreview() {
    DialogTheme {
        LoginSelectionScreen(
            onGithubLogin = {},
            onAppleLogin = {},
        )
    }
}
