package com.on.dialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.main.MainApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            DialogTheme {
                MainApp()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainApp()
}
