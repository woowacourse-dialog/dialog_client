plugins {
    id("dialog.convention.kotlin.feature.impl")
}

android {
    namespace = "com.on.dialog.feature.main.impl"
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.main.api)
            implementation(projects.feature.discussiondetail.api)
        }
    }
}