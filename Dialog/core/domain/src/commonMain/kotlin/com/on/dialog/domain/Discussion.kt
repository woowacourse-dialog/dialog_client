package com.on.dialog.domain

sealed interface Discussion {
    val content: Content
    val summary: String?
    val endDate: String
}
