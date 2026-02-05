import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("dialog.convention.kmp.library")
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.on.dialog.core.data"
}

buildkonfig {
    packageName = "com.on.dialog.core.data"

    defaultConfigs {
        buildConfigField(
            STRING,
            "BASE_URL",
            "${gradleLocalProperties(rootDir, providers).getProperty("debug_base_url")}"
        )
        buildConfigField(BOOLEAN, "IS_DEBUG", "true")
    }

    defaultConfigs("release") {
        buildConfigField(
            STRING,
            "BASE_URL",
            "${gradleLocalProperties(rootDir, providers).getProperty("release_base_url")}"
        )
        buildConfigField(BOOLEAN, "IS_DEBUG", "false")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.datetime)

            // project
            implementation(projects.core.network)
            implementation(projects.core.domain)
            implementation(projects.core.model)
            implementation(projects.core.local)
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
