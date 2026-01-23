plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
    id("dialog.convention.kotlin.serialization")
}

android {
    namespace = "com.on.dialog.core.designsystem"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.materialIconsExtended)

            // Kotlinx
            implementation(libs.kotlinx.collections.immutable)

            implementation(libs.androidx.nav3.runtime)
            implementation(libs.kotlinx.serialization.gradle.plugin)
        }
    }
}