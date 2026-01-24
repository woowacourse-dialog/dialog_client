package com.on.dialog.scrap.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.scrap.api.ScrapNavKey
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import com.on.dialog.scrap.impl.navigation.scrapScreen
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class ScrapNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(ScrapNavKey::class, ScrapNavKey.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        scrapScreen(navigator)
    }
}
