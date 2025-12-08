package com.on.convention

import com.android.build.api.dsl.LibraryExtension
import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.configureAndroid
import com.on.convention.extension.configureKmp
import com.on.convention.extension.libs
import com.on.convention.extension.versionInt
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryPlugin :
    BuildLogicPlugin({
        applyPlugins(Plugins.ANDROID_LIBRARY, Plugins.KOTLIN_ANDROID)

        extensions.configure<LibraryExtension> {
            configureAndroid()
            configureKmp()

            testOptions {
                targetSdk = libs.versionInt("targetSdk")
            }
        }
    })
