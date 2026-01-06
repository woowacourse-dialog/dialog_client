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

            // datetime
            implementation(libs.kotlinx.datetime)

            // project
            implementation(projects.core.domain)
            implementation(projects.core.model)
            implementation(projects.core.common)
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
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
}