plugins {
    `kotlin-dsl`
}

repositories {
    google {
        content {
            includeGroupByRegex("com\\.android.*")
            includeGroupByRegex("com\\.google.*")
            includeGroupByRegex("androidx.*")
        }
    }
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    compileOnly(libs.bundles.plugins)
}

gradlePlugin {
    plugins {
        register("convention.andorid.application") {
            id = "dialog.convention.android.application"
            implementationClass = "com.on.convention.AndroidApplicationPlugin"
        }

        register("convention.android.application.compose") {
            id = "dialog.convention.android.application.compose"
            implementationClass = "com.on.convention.AndroidApplicationComposePlugin"
        }

        register("convention.android.library") {
            id = "dialog.convention.android.library"
            implementationClass = "com.on.convention.AndroidLibraryPlugin"
        }

        register("convention.jvm.kotlin") {
            id = "dialog.convention.jvm.kotlin"
            implementationClass = "com.on.convention.JvmKotlinPlugin"
        }

        register("convention.kotest") {
            id = "dialog.convention.kotest"
            implementationClass = "com.on.convention.KotestConventionPlugin"
        }

        register("convention.kmp") {
            id = "dialog.convention.kmp"
            implementationClass = "com.on.convention.KmpPlugin"
        }

        register("convention.kmp.android") {
            id = "dialog.convention.kmp.android"
            implementationClass = "com.on.convention.KmpAndroidPlugin"
        }

        register("convention.kmp.ios") {
            id = "dialog.convention.kmp.ios"
            implementationClass = "com.on.convention.KmpIosPlugin"
        }

        register("convention.kmp.compose") {
            id = "dialog.convention.kmp.compose"
            implementationClass = "com.on.convention.KmpComposePlugin"
        }

        register("convention.kmp.feature") {
            id = "dialog.convention.kmp.feature"
            implementationClass = "com.on.convention.KmpFeaturePlugin"
        }

        register("convention.kotlin.serialization") {
            id = "dialog.convention.kotlin.serialization"
            implementationClass = "com.on.convention.KotlinSerializationPlugin"
        }

        register("convention.room") {
            id = "dialog.convention.room"
            implementationClass = "com.on.convention.RoomPlugin"
        }
    }
}
