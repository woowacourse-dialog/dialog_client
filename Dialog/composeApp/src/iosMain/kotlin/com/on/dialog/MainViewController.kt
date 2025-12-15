package com.on.dialog

import androidx.compose.ui.window.ComposeUIViewController
import com.on.dialog.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
