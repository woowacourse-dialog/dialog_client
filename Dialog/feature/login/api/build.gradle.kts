plugins {
    id("dialog.convention.kotlin.feature.api")
}

android {
    namespace = "com.on.dialog.feature.login.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
