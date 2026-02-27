plugins {
    id("dialog.convention.kotlin.feature.impl")
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.feature.mypage.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.login.api)
            implementation(projects.feature.mycreated.api)
            implementation(projects.feature.scrap.api)

            implementation(libs.imagepicker)
        }
    }
}
