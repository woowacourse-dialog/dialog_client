package com.on.dialog

import androidx.compose.ui.window.ComposeUIViewController
import com.on.dialog.di.initKoin
import com.on.dialog.di.initLogger

fun MainViewController() = ComposeUIViewController(
    configure = {
        initLogger()
        initKoin()
    }
) { App() }
