plugins {
    id("dialog.convention.kotlin.feature.impl")

}

android {
    namespace = "com.on.dialog.feature.creatediscussion.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.creatediscussion.api)
            // Markdown
            implementation(libs.multiplatform.markdown.renderer)
            implementation(libs.multiplatform.markdown.renderer.m3)
        }
    }
}
