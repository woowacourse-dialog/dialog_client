package com.on.convention

import com.on.convention.extension.applyPlugins

internal class KmpFeaturePlugin :
    BuildLogicPlugin(
        {
            applyPlugins(
                "dialog.convention.kmp",
                "dialog.convention.kmp.android",
                "dialog.convention.kmp.ios",
                "dialog.convention.kmp.compose",
            )
        },
    )
