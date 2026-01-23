package com.on.dialog.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class SavedStateConfigurationProvider(
    val providers: List<NavKeyProvider>,
) {
    val savedStateConfiguration: SavedStateConfiguration by lazy {
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    providers.forEach { provider ->
                        with(provider) {
                            registerNavKeys()
                        }
                    }
                }
            }
        }
    }
}
