package com.on.dialog.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.api.DiscussionDetailNavKey
import com.on.navigation.NavKeyProvider
import com.on.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class DiscussionDetailNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(DiscussionDetailNavKey::class, DiscussionDetailNavKey.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        discussionDetailScreen(navigator)
    }
}
