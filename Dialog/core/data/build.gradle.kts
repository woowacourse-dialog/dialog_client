plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.data"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}