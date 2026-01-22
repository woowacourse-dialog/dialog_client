plugins {
    id("dialog.convention.kotlin.feature.impl")
}

android {
    namespace = "com.on.dialog.feature.mypage"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.login.api)
        }
    }
}
