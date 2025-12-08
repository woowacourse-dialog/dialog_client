package com.on.convention

import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.kotlin
import com.on.convention.extension.library
import com.on.convention.extension.libs

internal class KotlinSerializationPlugin :
    BuildLogicPlugin(
        {
            applyPlugins(Plugins.KOTLINX_SERIALIZATION)

            kotlin {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(libs.library("kotlinx-serialization-json"))
                    }
                }
            }
        },
    )
