package com.on.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.on.buildlogic.convention.extension.PluginIds
import com.on.buildlogic.convention.extension.configureDialogTargets
import com.on.buildlogic.convention.extension.configureIosFrameworkForApp
import com.on.buildlogic.convention.extension.libs
import com.on.buildlogic.convention.extension.versionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply {
            apply(PluginIds.KOTLIN_MULTIPLATFORM)
            apply(PluginIds.ANDROID_APPLICATION)
            apply("dialog.convention.kmp.compose")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configureDialogTargets()
            configureIosFrameworkForApp()
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.versionInt("compileSdk")
            defaultConfig {
                minSdk = libs.versionInt("minSdk")
                targetSdk = libs.versionInt("targetSdk")
            }
            buildFeatures { compose = true }
        }
    }
}
