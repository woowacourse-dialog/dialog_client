plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.core.designsystem"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.material.icons.extended)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}
