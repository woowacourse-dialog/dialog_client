plugins {
    id("dialog.convention.kmp.library")
    id("dialog.convention.kmp.compose")
}

android {
    namespace = "com.on.dialog.feature.mypage"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.network)
            implementation(projects.core.local)
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(projects.feature.login)

            // koin
            implementation(libs.koin.compose.viewmodel)

            // immutable
            implementation(libs.kotlinx.collections.immutable)

            // image
            implementation(libs.imagepicker)
        }
    }
}