plugins {
    id("dialog.convention.kotlin.feature.impl")

}

android {
    namespace = "com.on.dialog.feature.main"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.mypage.impl)
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.discussionlist.impl)
            implementation(projects.feature.scrap.api)
            implementation(projects.feature.scrap.impl)
        }
    }
}
