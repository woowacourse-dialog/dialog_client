package com.on.dialog.di

import com.on.dialog.AndroidPlatform
import com.on.dialog.Platform
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<Platform> { AndroidPlatform() }
    }
