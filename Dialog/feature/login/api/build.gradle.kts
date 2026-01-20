plugins {
    id("dialog.convention.kmp.library")
    alias(libs.plugins.ksp.gradle.plugin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.on.dialog.feature.login.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.material3.adaptive.nav3)
            implementation(libs.androidx.material3.adaptive)
        }
    }
}
