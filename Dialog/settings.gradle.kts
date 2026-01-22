rootProject.name = "Dialog"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core:designsystem")
include(":core:data")
include(":core:domain")
include(":core:network")
include(":core:model")
include(":core:ui")
include(":core:common")
include(":core:local")
include(":core:navigation")
include(":feature:mypage:api")
include(":feature:mypage:impl")
include(":feature:login:api")
include(":feature:login:impl")
include(":feature:main:api")
include(":feature:main:impl")
include(":feature:scrap:impl")
include(":feature:scrap:api")
include(":feature:discussiondetail:api")
include(":feature:discussiondetail:impl")
