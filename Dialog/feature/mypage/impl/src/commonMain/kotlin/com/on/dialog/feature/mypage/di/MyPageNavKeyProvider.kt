package com.on.dialog.feature.mypage.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.mypage.api.MyPageNavKey
import com.on.dialog.feature.mypage.navigation.myPageScreen
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class MyPageNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(MyPageNavKey::class, MyPageNavKey.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        myPageScreen(navigator)
    }
}
