package com.on.convention

import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal class JvmKotlinPlugin :
    BuildLogicPlugin({
        applyPlugins(Plugins.JAVA_LIBRARY, Plugins.KOTLIN_JVM)

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(21)
        }
    })
