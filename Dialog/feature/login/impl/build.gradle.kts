import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("dialog.convention.kotlin.feature.impl")
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
                "${
                    gradleLocalProperties(
                        rootDir,
                        providers
                    ).getProperty("release_github_oauth_url")
                }"
            )
            buildConfigField(BOOLEAN, "IS_DEBUG", "false")
        }
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.napier)
        }
        commonMain.dependencies {
            implementation(projects.core.local)
            implementation(projects.feature.login.api)
            implementation(projects.feature.signup.api)
        }
        iosMain.dependencies {
            implementation(libs.napier)
        }
    }
}