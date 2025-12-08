package com.on.convention

import com.android.build.api.dsl.ApplicationExtension
import com.on.convention.extension.Plugins
import com.on.convention.extension.applyPlugins
import com.on.convention.extension.configureAndroid
import com.on.convention.extension.configureKmp
import com.on.convention.extension.libs
import com.on.convention.extension.version
import com.on.convention.extension.versionInt
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationPlugin :
    BuildLogicPlugin(
        {
            applyPlugins(Plugins.ANDROID_APPLICATION)

            extensions.configure<ApplicationExtension> {
                configureAndroid()
                configureKmp()

                defaultConfig {
                    applicationId = libs.version("packageName")
                    minSdk = libs.versionInt("minSdk")
                    targetSdk = libs.versionInt("targetSdk")
                    versionCode = libs.versionInt("versionCode")
                    versionName = libs.version("versionName")
                }
            }
        },
    )
