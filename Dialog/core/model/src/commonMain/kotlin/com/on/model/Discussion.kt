package com.on.model

sealed interface Discussion {
    val content: Content
    val summary: String?
}
