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
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(PluginIds.KOTLIN_MULTIPLATFORM)
        plugins.apply(PluginIds.ANDROID_LIBRARY)
        plugins.apply(PluginIds.COMPOSE_MULTIPLATFORM)
        plugins.apply(PluginIds.KOTLIN_COMPOSE)
        plugins.apply(PluginIds.KOTLINX_SERIALIZATION)

        extensions.configure<KotlinMultiplatformExtension> {
            configureDialogTargets()
            configureIosFrameworkForLibrary(project)

            sourceSets.named("commonMain") {
                dependencies {
                    // compose 공통
                    implementation("org.jetbrains.compose.runtime:runtime:1.10.0")
                    implementation("org.jetbrains.compose.foundation:foundation:1.10.0")
                    implementation("org.jetbrains.compose.material3:material3:1.9.0")
                    implementation("org.jetbrains.compose.ui:ui:1.10.0")
                    implementation("org.jetbrains.compose.components:components-resources:1.10.0")
                    implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0")
                    implementation(libs.library("koin-compose"))

                    // core 공통
                    implementation(project(":core:ui"))
                    implementation(project(":core:network"))
                    implementation(project(":core:domain"))
                    implementation(project(":core:data"))
                    implementation(project(":core:local"))
                    implementation(project(":core:model"))
                    implementation(project(":core:navigation"))
                    implementation(project(":core:designsystem"))
                    implementation(project(":core:common"))

                    // Feature 공통
                    implementation(libs.library("koin-compose-viewmodel"))
                    implementation(libs.library("kotlinx-collections-immutable"))
                    implementation(libs.library("androidx-nav3-runtime"))
                    implementation(libs.library("napier"))
                }
            }
        }

        dependencies {
            add("debugImplementation", "org.jetbrains.compose.ui:ui-tooling:1.10.0")
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