import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN

plugins {
    id("dialog.convention.kmp.library")
    alias(libs.plugins.ksp.gradle.plugin)
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.on.dialog.core.network"
}

buildkonfig {
    packageName = "com.on.dialog.core.network"

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
            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

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
            implementation(libs.ktor.client.logging)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.ktor.client.logging)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
}