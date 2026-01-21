package com.on.dialog.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.on.navigation.NavKeyProvider
import io.github.aakira.napier.Napier
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class AppNavConfig(
    val providers: List<NavKeyProvider>
) {
    val savedStateConfiguration: SavedStateConfiguration by lazy {
        println("실제 사용 시점 - providers: ${providers}")
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
