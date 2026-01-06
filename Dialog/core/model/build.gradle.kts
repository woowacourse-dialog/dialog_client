plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.model"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(projects.core.common)
        }
    }
}
