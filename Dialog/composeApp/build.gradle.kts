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

            // discussionlist
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.discussionlist.impl)
            // main
            implementation(projects.feature.main)
            // login
            implementation(projects.feature.login.impl)
            // mypage
            implementation(projects.feature.mypage.impl)
            // scrap
            implementation(projects.feature.scrap.impl)
            // discussiondetail
            implementation(projects.feature.discussiondetail.impl)

            //nav3
            implementation(libs.jetbrains.navigation3.ui)
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
