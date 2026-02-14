plugins {
    id("dialog.convention.kotlin.feature.impl")
}

android {
    namespace = "com.on.dialog.feature.mycreated.impl"
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mycreated.api)
            implementation(projects.feature.discussiondetail.api)
        }
    }
}
