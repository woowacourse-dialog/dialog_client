plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kotlin.serialization")
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
