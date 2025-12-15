package com.on.buildlogic.convention

import com.on.buildlogic.convention.extension.PluginIds
import com.on.buildlogic.convention.extension.compose
import com.on.buildlogic.convention.extension.library
import com.on.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(PluginIds.COMPOSE_MULTIPLATFORM)
        plugins.apply(PluginIds.KOTLIN_COMPOSE)

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.named("commonMain") {
                dependencies {
                    implementation(compose.dependencies.runtime)
                    implementation(compose.dependencies.foundation)
                    implementation(compose.dependencies.material3)
                    implementation(compose.dependencies.ui)
                    implementation(compose.dependencies.components.resources)
                    implementation(compose.dependencies.components.uiToolingPreview)
                    implementation(libs.library("koin-compose"))
                }
            }

            sourceSets
                .matching { it.name == "androidMain" }
                .all {
                    dependencies {
                        implementation(compose.dependencies.preview)
                        implementation(libs.library("androidx.activity.compose"))
                    }
                }
        }

        dependencies {
            add("debugImplementation", compose.dependencies.uiTooling)
        }
    }
}
