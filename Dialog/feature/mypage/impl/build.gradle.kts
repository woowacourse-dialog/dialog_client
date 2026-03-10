import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.buildkonfig)
    id("dialog.convention.kotlin.feature.impl")
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.feature.mypage.impl"
}

buildkonfig {
    packageName = "com.on.dialog.feature.mypage.impl"

    defaultConfigs {
        buildConfigField(
            STRING,
            "PRIVACY_POLICY_URL",
            "${gradleLocalProperties(rootDir, providers).getProperty("privacy_policy_url")}"
        )
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.login.api)
            implementation(projects.feature.mycreated.api)
            implementation(projects.feature.scrap.api)

            implementation(libs.imagepicker)
        }
    }
}
