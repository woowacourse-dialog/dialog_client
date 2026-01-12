plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.local"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // dataStore
            implementation(libs.androidx.datastore.preferences)
        }
    }
}
