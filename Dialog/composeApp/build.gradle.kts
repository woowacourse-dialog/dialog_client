plugins {
    id("dialog.convention.kmp.application")
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
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
