plugins {
    id("dialog.convention.kmp.library")
    alias(libs.plugins.ksp.gradle.plugin)
}

android {
    namespace = "com.on.dialog.core.network"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)

            // ktorfit
            implementation(libs.ktorfit)

            // project
            implementation(project(":core:data"))
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    kspAndroid(libs.ktorfit.ksp)
}