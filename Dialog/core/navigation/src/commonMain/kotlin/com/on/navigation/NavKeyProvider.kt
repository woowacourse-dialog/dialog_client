package com.on.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.PolymorphicModuleBuilder

interface NavKeyProvider {
    /**
     * Feature의 NavKey를 SerializersModule에 등록
     */
    fun PolymorphicModuleBuilder<NavKey>.registerNavKeys()

    /**
     * Feature의 화면을 EntryProvider에 등록
     */
    fun EntryProviderScope<NavKey>.registerScreens(navigator: Navigator)
}
