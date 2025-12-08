package com.on.convention.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun DependencyHandlerScope.kspKmp(artifact: MinimalExternalModuleDependency) {
    add("kspAndroid", artifact)
    add("kspIosX64", artifact)
    add("kspIosArm64", artifact)
    add("kspIosSimulatorArm64", artifact)
}
