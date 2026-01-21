plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kotlin.serialization")
}

android {
    namespace = "com.on.dialog.feature.login.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.nav3.runtime)
        }
    }
}
