plugins {
    id("dialog.convention.kotlin.feature.impl")

}

android {
    namespace = "com.on.dialog.feature.main"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // feature

            // login
            implementation(projects.feature.login.api)
            implementation(projects.feature.login.impl)
            // mypage
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.mypage.impl)
            // discussionlist
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.discussionlist.impl)
            // scrap
            implementation(projects.feature.scrap.api)
            implementation(projects.feature.scrap.impl)
            // discussiondetail
            implementation(projects.feature.discussiondetail.api)
            implementation(projects.feature.discussiondetail.impl)
        }
    }
}
