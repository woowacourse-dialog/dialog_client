package com.on.dialog.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.login.LoginType
import com.on.dialog.feature.login.LoginWebViewScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    var showLoginWebView: Boolean by rememberSaveable { mutableStateOf(false) }

    when (showLoginWebView) {
        true -> {
            LoginWebViewScreen(
                loginType = LoginType.GITHUB,
                onLoginSuccess = { showLoginWebView = false },
                onLoginFailure = { showLoginWebView = false },
            )
        }

        false -> {
            MyPageScreenDefault(onLoginClick = { showLoginWebView = true })
        }
    }
}

@Composable
fun MyPageScreenDefault(
    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        DialogButton(
            text = "로그인",
            onClick = onLoginClick,
        )
        DialogButton(
            text = "로그아웃",
            onClick = onLogoutClick,
        )
        DialogButton(
            text = "API 실험",
            onClick = {
            },
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    DialogTheme {
        Surface {
            MyPageScreenDefault()
        }
    }
}
