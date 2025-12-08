package com.on.convention

import com.android.build.api.dsl.ApplicationExtension
import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationComposePlugin :
    BuildLogicPlugin(
        {
            applyPlugins(Plugins.ANDROID_APPLICATION, Plugins.KOTLIN_COMPOSE)

            extensions.configure<ApplicationExtension> {
                configureCompose(this)
            }
        },
    )
