package com.on.buildlogic.convention

import com.android.build.api.dsl.LibraryExtension
import com.on.buildlogic.convention.extension.PluginIds
import com.on.buildlogic.convention.extension.configureDialogTargets
import com.on.buildlogic.convention.extension.configureIosFrameworkForLibrary
import com.on.buildlogic.convention.extension.libs
import com.on.buildlogic.convention.extension.versionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply(PluginIds.KOTLIN_MULTIPLATFORM)
            plugins.apply(PluginIds.ANDROID_LIBRARY)

            extensions.configure<KotlinMultiplatformExtension> {
                configureDialogTargets()
                configureIosFrameworkForLibrary(project)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = libs.versionInt("compileSdk")
                defaultConfig { minSdk = libs.versionInt("minSdk") }
            }
        }
    }
}
