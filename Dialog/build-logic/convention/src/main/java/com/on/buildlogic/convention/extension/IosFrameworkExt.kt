package com.on.buildlogic.convention.extension

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.configureIosFramework(
    baseName: String,
    isStatic: Boolean = true,
) {
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            this.baseName = baseName
            this.isStatic = isStatic
        }
    }
}

internal fun KotlinMultiplatformExtension.configureIosFrameworkForLibrary(project: Project) {
    configureIosFramework(
        baseName = project.name,
        isStatic = true,
    )
}

internal fun KotlinMultiplatformExtension.configureIosFrameworkForApp() {
    configureIosFramework(
        baseName = "ComposeApp",
        isStatic = true,
    )
}
