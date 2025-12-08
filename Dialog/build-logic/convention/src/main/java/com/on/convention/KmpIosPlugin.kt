package com.on.convention

import com.on.convention.extension.kotlin

internal class KmpIosPlugin :
    BuildLogicPlugin(
        {
            kotlin {
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }
        },
    )
