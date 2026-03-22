package com.on.dialog.feature.creatediscussion.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.creatediscussion.api.CreateDiscussionNavKey
import com.on.dialog.feature.creatediscussion.api.EditDiscussionNavKey
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class CreateDiscussionNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(
            subclass = CreateDiscussionNavKey::class,
            serializer = CreateDiscussionNavKey.serializer(),
        )
        subclass(
            subclass = EditDiscussionNavKey::class,
            serializer = EditDiscussionNavKey.serializer(),
        )
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        createDiscussionScreen(navigator)
        editDiscussionScreen(navigator)
    }
}
