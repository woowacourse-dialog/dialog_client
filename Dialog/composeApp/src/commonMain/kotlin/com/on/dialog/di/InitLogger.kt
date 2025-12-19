package com.on.dialog.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initLogger() {
    Napier.base(DebugAntilog())
}
