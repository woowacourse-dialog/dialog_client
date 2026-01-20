plugins {
    id("dialog.convention.kmp.library")
    alias(libs.plugins.ksp.gradle.plugin)
    alias(libs.plugins.kotlin.serialization)
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.core.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.material3.adaptive.nav3)
            implementation(libs.androidx.material3.adaptive)
            implementation(projects.core.designsystem)
        }
    }
}