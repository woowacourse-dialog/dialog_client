import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
    id("dialog.convention.kotlin.serialization")
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.on.dialog.feature.login.impl"
}

buildkonfig {
    packageName = "com.on.dialog.feature.login.impl"

    defaultConfigs {
        buildConfigField(
            STRING,
            "BASE_URL",
            "${gradleLocalProperties(rootDir, providers).getProperty("debug_base_url")}"
        )
        buildConfigField(
            STRING,
            "GITHUB_OAUTH_URL",
            "${gradleLocalProperties(rootDir, providers).getProperty("debug_github_oauth_url")}"
        )
        buildConfigField(BOOLEAN, "IS_DEBUG", "true")
    }

    targetConfigs {
        create("release") {
            buildConfigField(
                STRING,
                "BASE_URL",
                "${gradleLocalProperties(rootDir, providers).getProperty("release_base_url")}"
            )
            buildConfigField(
                STRING,
                "GITHUB_OAUTH_URL",
                "${gradleLocalProperties(rootDir, providers).getProperty("release_github_oauth_url")}"
            )
            buildConfigField(BOOLEAN, "IS_DEBUG", "false")
        }
    }
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

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.material3.adaptive.nav3)
            implementation(libs.androidx.material3.adaptive)
        }
    }
}