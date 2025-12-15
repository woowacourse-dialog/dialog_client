package com.on.buildlogic.convention

import com.on.buildlogic.convention.extension.PluginIds
import com.on.buildlogic.convention.extension.library
import com.on.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(PluginIds.KOTLINX_SERIALIZATION)

        kotlinExtension.sourceSets.apply {
            named("commonMain") {
                dependencies {
                    implementation(libs.library("kotlinx.serialization.json"))
                }
            }
        }
    }
}
