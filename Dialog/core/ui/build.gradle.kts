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
            implementation(projects.core.designsystem)
            implementation(projects.core.model)

            // Kotlinx
            implementation(libs.kotlinx.collections.immutable)

            // Image
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Network
            implementation(libs.ktor.client.core)

            // Markdown
            implementation(libs.multiplatform.markdown.renderer)
            implementation(libs.multiplatform.markdown.renderer.m3)

            // Navigation event
            implementation(libs.compose.navigationevent)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
