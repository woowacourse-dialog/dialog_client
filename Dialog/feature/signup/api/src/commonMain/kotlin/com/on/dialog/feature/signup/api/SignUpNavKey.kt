package com.on.dialog.feature.signup.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class SignUpNavKey(
    val jsessionId: String,
) : NavKey
