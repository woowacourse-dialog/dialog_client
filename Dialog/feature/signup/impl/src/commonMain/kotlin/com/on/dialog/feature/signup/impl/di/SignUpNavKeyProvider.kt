package com.on.dialog.feature.signup.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.signup.api.SignUpNavKey
import com.on.dialog.feature.signup.impl.navigation.signUpScreen
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class SignUpNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(
            subclass = SignUpNavKey::class,
            serializer = SignUpNavKey.serializer(),
        )
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        signUpScreen(navigator)
    }
}
