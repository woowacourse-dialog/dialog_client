package com.on.convention.extension

import androidx.room.gradle.RoomExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.libraryAndroidOptions(configure: LibraryAndroidComponentsExtension.() -> Unit) {
    extensions.configure(configure)
}

internal fun Project.room(action: RoomExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.configureAndroid() {
    android {
        namespace?.let {
            this.namespace = it
        }

        compileSdkVersion(libs.versionInt("compileSdk"))

        defaultConfig {
            minSdk = libs.versionInt("minSdk")
            targetSdk = libs.versionInt("targetSdk")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
            isCoreLibraryDesugaringEnabled = true
        }

        dependencies {
            coreLibraryDesugaring(libs.library("desugar.jdk.libs"))
        }

        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(21)
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        (this as CommonExtension<*, *, *, *, *, *>).lint {
            val filename = displayName.replace(":", "_").replace("[\\s']".toRegex(), "")

            xmlReport = true
            xmlOutput =
                rootProject.layout.buildDirectory
                    .file("lint-reports/lint-results-$filename.xml")
                    .get()
                    .asFile
            htmlReport = true
            htmlOutput =
                rootProject.layout.buildDirectory
                    .file("lint-reports/lint-results-$filename.html")
                    .get()
                    .asFile
            sarifReport = false
        }
    }
}
