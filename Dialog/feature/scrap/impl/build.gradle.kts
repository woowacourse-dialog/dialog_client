plugins {
    id("dialog.convention.kotlin.feature.impl")
}

android {
    namespace = "com.on.dialog.feature.scrap.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.scrap.api)
            implementation(projects.feature.discussiondetail.api)
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.login.api)
        }
    }
}
