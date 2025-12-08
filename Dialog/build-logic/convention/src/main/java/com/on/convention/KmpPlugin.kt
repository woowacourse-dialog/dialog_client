package com.on.convention

import com.on.convention.extension.applyPlugins
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class KmpPlugin :
    BuildLogicPlugin(
        {
            applyPlugins("org.jetbrains.kotlin.multiplatform")

            tasks.withType(KotlinCompile::class.java) {
                compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
            }
        },
    )
