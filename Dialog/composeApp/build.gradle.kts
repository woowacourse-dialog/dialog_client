plugins {
    id("dialog.convention.kmp.application")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.data)

            implementation(projects.feature.main)
            implementation(projects.feature.login.impl)
            implementation(projects.feature.signup.impl)
            implementation(projects.feature.scrap.impl)
            implementation(projects.feature.mypage.impl)
            implementation(projects.feature.mycreated.impl)
            implementation(projects.feature.discussionlist.impl)
            implementation(projects.feature.discussiondetail.impl)
            implementation(projects.feature.creatediscussion.impl)
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
