plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kotlin.serialization")
}

android {
    namespace = "com.on.dialog.feature.main.api"
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
