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
            implementation(projects.core.navigation)
            implementation(projects.feature.mypage.api)
            implementation(projects.feature.login.api)

            // koin
            implementation(libs.koin.compose.viewmodel)

            // navigation
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.nav3.runtime)
        }
    }
}