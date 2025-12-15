package com.on.dialog.di

import com.on.dialog.IOSPlatform
import com.on.dialog.Platform
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<Platform> { IOSPlatform() }
    }
