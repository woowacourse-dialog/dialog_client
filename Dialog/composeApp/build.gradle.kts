plugins {
    id("dialog.convention.kmp.application")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // core
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.network)
            implementation(projects.core.data)
            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.model)
            implementation(projects.core.local)
            implementation(projects.core.navigation)
            // feature
            implementation(projects.feature.login.impl)
            implementation(projects.feature.mypage.impl)
            implementation(projects.feature.main.impl)
            implementation(projects.feature.scrap.impl)

            implementation(projects.feature.login.api)
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.main.api)
            implementation(projects.feature.scrap.api)

            implementation(libs.androidx.nav3.ui)

            implementation(libs.kotlinx.collections.immutable)
        }
    }
}

android {
    namespace = "com.on.dialog"

    defaultConfig {
        applicationId = "com.on.dialog"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".dev"
            versionNameSuffix = ".dev"
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name_dev",
            )
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name",
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}
