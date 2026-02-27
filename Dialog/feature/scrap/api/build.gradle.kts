plugins {
    id("dialog.convention.kotlin.feature.api")
}

android {
    namespace = "com.on.dialog.feature.scrap.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
