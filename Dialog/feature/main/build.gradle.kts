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
            implementation(projects.feature.login.impl)
            implementation(projects.feature.mypage.impl)
            implementation(projects.feature.discussionlist.impl)
            implementation(projects.feature.scrap.impl)
            implementation(projects.feature.discussiondetail.impl)

            implementation(projects.feature.login.api)
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.scrap.api)
            implementation(projects.feature.discussiondetail.api)
            
            implementation(libs.androidx.nav3.ui)

            implementation(libs.kotlinx.collections.immutable)
        }
    }
}
