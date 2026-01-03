plugins {
    id("dialog.convention.kmp.library")
}

android {
    namespace = "com.on.dialog.core.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
    }
}
