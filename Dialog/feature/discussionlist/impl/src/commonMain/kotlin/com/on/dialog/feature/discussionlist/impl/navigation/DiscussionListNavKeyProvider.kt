package com.on.dialog.feature.discussionlist.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class DiscussionListNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(
            subclass = DiscussionListNavKey::class,
            serializer = DiscussionListNavKey.serializer(),
        )
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        discussionListScreen(navigator)
    }
}
