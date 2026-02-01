package com.on.dialog

import androidx.compose.ui.window.ComposeUIViewController
import com.on.dialog.di.initKoin
import com.on.dialog.di.initLogger
import com.on.dialog.main.MainApp

fun MainViewController() = ComposeUIViewController(
    configure = {
        initLogger()
        initKoin()
    }
) {
    MainApp()
}
