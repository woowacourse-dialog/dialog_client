package com.on.dialog.feature.mycreated.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.mycreated.api.MyCreatedNavKey
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class MyCreatedNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(
            subclass = MyCreatedNavKey::class,
            serializer = MyCreatedNavKey.serializer(),
        )
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        myCreatedScreen(navigator)
    }
}
