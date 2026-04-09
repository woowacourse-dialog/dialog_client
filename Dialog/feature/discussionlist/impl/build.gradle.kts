plugins {
    id("dialog.convention.kotlin.feature.impl")
}

android {
    namespace = "com.on.dialog.feature.discussionlist.impl"
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.discussionlist.api)
            implementation(projects.feature.discussiondetail.api)
            implementation(projects.feature.creatediscussion.api)
        }
    }
}
