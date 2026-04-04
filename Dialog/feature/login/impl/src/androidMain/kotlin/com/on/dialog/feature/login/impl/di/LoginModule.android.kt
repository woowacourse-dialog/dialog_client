package com.on.dialog.feature.login.impl.di

import com.on.dialog.feature.login.impl.model.AppleSignInHandler
import com.on.dialog.feature.login.impl.model.OAuthSignInHandler
import org.koin.core.module.Module
import org.koin.core.qualifier.named

actual fun Module.registerAppleSignInHandler() {
    single<OAuthSignInHandler>(qualifier = named<AuthQualifier.Apple>()) { AppleSignInHandler() }
}
