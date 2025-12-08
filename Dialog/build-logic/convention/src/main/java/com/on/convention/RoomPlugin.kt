package com.on.convention

import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.kotlin
import com.on.convention.extension.kspKmp
import com.on.convention.extension.library
import com.on.convention.extension.libs
import com.on.convention.extension.room
import org.gradle.kotlin.dsl.dependencies

internal class RoomPlugin :
    BuildLogicPlugin(
        {
            applyPlugins(Plugins.ANDROIDX_ROOM, Plugins.KSP)

            kotlin {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(libs.library("androidx-room-runtime"))
                        implementation(libs.library("androidx.sqlite.bundled"))
                    }
                }
            }

            dependencies {
                kspKmp(libs.library("androidx.room.compiler"))
            }

            room {
                schemaDirectory("$projectDir/schemas")
            }
        },
    )
