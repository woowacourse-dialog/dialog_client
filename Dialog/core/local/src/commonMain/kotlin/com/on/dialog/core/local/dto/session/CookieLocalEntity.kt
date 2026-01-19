package com.on.dialog.core.local.dto.session

import kotlinx.serialization.Serializable

@Serializable
data class CookieLocalEntity(
    val name: String,
    val value: String,
    val domain: String,
    val path: String,
    val expires: Long? = null,
    val secure: Boolean = true,
    val httpOnly: Boolean = true,
)
