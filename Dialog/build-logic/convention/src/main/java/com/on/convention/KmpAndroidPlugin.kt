package com.on.convention

import com.on.convention.extension.Plugins
import com.on.convention.extension.android
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.configureAndroid
import com.on.convention.extension.kotlin
import com.on.convention.extension.libraryAndroidOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal class KmpAndroidPlugin :
    BuildLogicPlugin(
        {
            applyPlugins(Plugins.ANDROID_LIBRARY)

            kotlin {
                androidTarget {
                    compilations.all {
                        libraryAndroidOptions {
                            compileTaskProvider.configure {
                                compilerOptions {
                                    jvmTarget.set(JvmTarget.JVM_21)
                                }
                            }
                        }
                    }
                }
            }

            android {
                configureAndroid()
            }
        },
    )
