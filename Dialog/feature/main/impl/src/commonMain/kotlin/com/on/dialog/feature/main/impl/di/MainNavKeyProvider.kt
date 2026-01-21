package com.on.dialog.feature.main.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.main.api.MainRoute
import com.on.dialog.feature.main.impl.navigation.mainScreen
import com.on.navigation.NavKeyProvider
import com.on.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class MainNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(MainRoute::class, MainRoute.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        mainScreen(navigator)
    }
}
