plugins {
    id("dialog.convention.kotlin.feature.impl")
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.feature.signup.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.signup.api)
            implementation(projects.feature.discussionlist.api)
        }
    }
}