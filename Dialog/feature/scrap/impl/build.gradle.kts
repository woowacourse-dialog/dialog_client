plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
    id("dialog.convention.kotlin.serialization")
}

android {
    namespace = "com.on.dialog.feature.scrap.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.network)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.local)
            implementation(projects.core.model)
            implementation(projects.core.navigation)
            implementation(projects.core.designsystem)


            // koin
            implementation(libs.koin.compose.viewmodel)

            // math
            implementation(libs.kotlinx.collections.immutable)

            implementation(projects.feature.scrap.api)
            implementation(projects.feature.discussiondetail.api)

            implementation(libs.androidx.nav3.runtime)
        }
    }
}
