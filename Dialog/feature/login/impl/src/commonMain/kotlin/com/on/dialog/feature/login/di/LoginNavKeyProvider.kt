package com.on.dialog.feature.login.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginNavKey
import com.on.dialog.feature.login.navigation.loginScreen
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class LoginNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(LoginNavKey::class, LoginNavKey.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        loginScreen(navigator)
    }
}
