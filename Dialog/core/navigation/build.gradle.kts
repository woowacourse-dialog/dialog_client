plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kotlin.serialization")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.core.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.lifecycle.viewmodelNavigation3)
        }
    }
}