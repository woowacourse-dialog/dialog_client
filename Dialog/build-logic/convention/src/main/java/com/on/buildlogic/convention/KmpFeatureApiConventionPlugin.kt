package com.on.buildlogic.convention

import com.android.build.api.dsl.LibraryExtension
import com.on.buildlogic.convention.extension.PluginIds
import com.on.buildlogic.convention.extension.configureDialogTargets
import com.on.buildlogic.convention.extension.configureIosFrameworkForLibrary
import com.on.buildlogic.convention.extension.library
import com.on.buildlogic.convention.extension.libs
import com.on.buildlogic.convention.extension.versionInt
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(PluginIds.KOTLIN_MULTIPLATFORM)
        plugins.apply(PluginIds.ANDROID_LIBRARY)
        plugins.apply(PluginIds.KOTLINX_SERIALIZATION)

        extensions.configure<KotlinMultiplatformExtension> {
            configureDialogTargets()
            configureIosFrameworkForLibrary(project)

            sourceSets.named("commonMain") {
                dependencies {
                    implementation(libs.library("kotlinx-serialization-json"))
                    implementation(libs.library("androidx-nav3-runtime"))
                }
            }
        }

        extensions.configure<LibraryExtension> {
            compileSdk = libs.versionInt("compileSdk")
            defaultConfig { minSdk = libs.versionInt("minSdk") }

            buildFeatures.buildConfig = true

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
    }
}
