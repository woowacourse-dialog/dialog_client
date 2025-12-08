package com.on.convention.extension

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(alias: String): String =
    findVersion(alias)
        .orElseThrow { missingCatalogEntry("version", alias) }
        .requiredVersion

internal fun VersionCatalog.versionInt(alias: String): Int = version(alias).toInt()

internal fun VersionCatalog.library(alias: String): MinimalExternalModuleDependency =
    findLibrary(alias)
        .orElseThrow { missingCatalogEntry("library", alias) }
        .get()

internal fun VersionCatalog.bundle(alias: String): ExternalModuleDependencyBundle =
    findBundle(alias)
        .orElseThrow { missingCatalogEntry("bundle", alias) }
        .get()

private fun missingCatalogEntry(
    type: String,
    alias: String,
): IllegalArgumentException = IllegalArgumentException("Version catalog $type alias '$alias' not found in libs.versions.toml")

internal fun Project.applyPlugins(vararg plugins: String) {
    plugins.forEach(pluginManager::apply)
}

internal val Project.isAndroidProject: Boolean
    get() =
        pluginManager.hasPlugin(Plugins.ANDROID_APPLICATION) ||
            pluginManager.hasPlugin(Plugins.ANDROID_LIBRARY)

internal val Project.androidExtensions: CommonExtension<*, *, *, *, *, *>
    get() {
        return if (pluginManager.hasPlugin(Plugins.ANDROID_APPLICATION)) {
            extensions.getByType<BaseAppModuleExtension>()
        } else if (pluginManager.hasPlugin(Plugins.ANDROID_LIBRARY)) {
            extensions.getByType<LibraryExtension>()
        } else {
            throw GradleException("The provided project does not have the Android plugin applied. ($name)")
        }
    }
