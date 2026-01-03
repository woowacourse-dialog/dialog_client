plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.core.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:designsystem"))

            // Image
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Network
            implementation(libs.ktor.client.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}