plugins {
    id("dialog.convention.kotlin.feature.impl")

}

android {
    namespace = "com.on.dialog.feature.discussiondetail.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.discussiondetail.api)
        }
    }
}
