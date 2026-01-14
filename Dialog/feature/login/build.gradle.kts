plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.feature.login"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.network)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.local)
            implementation(projects.core.model)

            // koin
            implementation(libs.koin.compose.viewmodel)
        }
    }
}