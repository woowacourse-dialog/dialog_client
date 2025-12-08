plugins {
    id("dialog.convention.kmp")
    id("dialog.convention.kmp.ios")
    id("dialog.convention.kmp.compose")
    id("dialog.convention.android.application")
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.androidx.navigation.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.napier)
        }
    }
}

android {
    namespace = libs.versions.packageName.get()

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".dev"
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
