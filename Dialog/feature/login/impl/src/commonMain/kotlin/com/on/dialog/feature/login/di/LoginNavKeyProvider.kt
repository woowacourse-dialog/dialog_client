package com.on.dialog.feature.login.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.login.api.LoginRoute
import com.on.dialog.feature.login.navigation.loginScreen
import com.on.navigation.NavKeyProvider
import com.on.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class LoginNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(LoginRoute::class, LoginRoute.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        loginScreen(navigator)
    }
}
