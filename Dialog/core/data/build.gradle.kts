plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.data"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)

            // project
            implementation(projects.core.network)
            implementation(projects.core.domain)
            implementation(projects.core.model)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}