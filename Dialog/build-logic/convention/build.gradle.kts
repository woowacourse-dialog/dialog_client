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
        register("dialog.convention.kmp.library") {
            id = "dialog.convention.kmp.library"
            implementationClass = "com.on.buildlogic.convention.KmpLibraryConventionPlugin"
        }

        register("dialog.convention.kmp.compose") {
            id = "dialog.convention.kmp.compose"
            implementationClass = "com.on.buildlogic.convention.KmpComposeConventionPlugin"
        }

        register("dialog.convention.kmp.application") {
            id = "dialog.convention.kmp.application"
            implementationClass = "com.on.buildlogic.convention.KmpApplicationConventionPlugin"
        }

        register("dialog.convention.kotlin.serialization") {
            id = "dialog.convention.kotlin.serialization"
            implementationClass = "com.on.buildlogic.convention.KotlinSerializationConventionPlugin"
        }
    }
}
