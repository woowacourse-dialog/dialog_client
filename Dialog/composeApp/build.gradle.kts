plugins {
    id("dialog.convention.kmp.application")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // core
            implementation(projects.core.designsystem)
            implementation(projects.core.data)

            // main
            implementation(projects.feature.main)
            // discussionlist
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.discussionlist.impl)
            // login
            implementation(projects.feature.login.impl)
            // mypage
            implementation(projects.feature.mypage.impl)
            // scrap
            implementation(projects.feature.scrap.impl)
            // discussiondetail
            implementation(projects.feature.discussiondetail.impl)
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
