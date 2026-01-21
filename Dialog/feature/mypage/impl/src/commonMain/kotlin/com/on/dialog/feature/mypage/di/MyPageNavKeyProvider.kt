package com.on.dialog.feature.mypage.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.feature.mypage.api.MyPageRoute
import com.on.dialog.feature.mypage.navigation.myPageScreen
import com.on.navigation.NavKeyProvider
import com.on.navigation.Navigator
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class MyPageNavKeyProvider : NavKeyProvider {
    override fun PolymorphicModuleBuilder<NavKey>.registerNavKeys() {
        subclass(MyPageRoute::class, MyPageRoute.serializer())
    }

    override fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator) {
        myPageScreen(navigator)
    }
}
