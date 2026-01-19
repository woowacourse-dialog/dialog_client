package com.on.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.login.LoginType
import com.on.dialog.feature.login.LoginWebViewScreen
import com.on.dialog.feature.mypage.MyPageScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // TODO Navigation으로 마이그레이션
    var showLoginWebView: Boolean by rememberSaveable { mutableStateOf(value = false) }

    DialogTheme {
        Scaffold { innerPadding ->
            if (showLoginWebView) {
                LoginWebViewScreen(
                    loginType = LoginType.GITHUB,
                    goBack = { showLoginWebView = false },
                    navigateToSignUp = { showLoginWebView = false },
                )
            } else {
                MyPageScreen(
                    navigateToLogin = { showLoginWebView = true },
                    modifier = Modifier.padding(paddingValues = innerPadding),
                )
            }
        }
    }
}
