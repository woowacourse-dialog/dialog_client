plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.domain"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
