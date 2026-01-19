package com.on.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mypage.MyPageScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    DialogTheme {
        Scaffold { innerPadding ->
            MyPageScreen(modifier = Modifier.padding(paddingValues = innerPadding))
        }
    }
}
