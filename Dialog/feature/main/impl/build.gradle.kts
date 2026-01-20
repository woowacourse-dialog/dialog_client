plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
    id("dialog.convention.kotlin.serialization")
}

android {
    namespace = "com.on.dialog.feature.main.impl"
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
            implementation(projects.core.navigation)

            // koin
            implementation(libs.koin.compose.viewmodel)

            implementation(projects.feature.login.api)

            implementation(libs.androidx.material3.adaptive.nav3)
            implementation(libs.androidx.material3.adaptive)
        }
    }
}