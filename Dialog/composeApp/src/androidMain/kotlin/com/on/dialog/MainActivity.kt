package com.on.dialog

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.on.dialog.main.MainApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val isPhone = rememberIsPhone()
            requestedOrientation =
                if (isPhone) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            MainApp()
        }
    }

    @Composable
    private fun rememberIsPhone(): Boolean {
        val configuration = LocalConfiguration.current
        return remember { configuration.smallestScreenWidthDp < 600 }
    }
}
