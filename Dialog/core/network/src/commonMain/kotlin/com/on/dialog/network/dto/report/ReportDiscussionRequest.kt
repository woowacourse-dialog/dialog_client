package com.on.dialog.network.dto.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportDiscussionRequest(
    @SerialName("reason")
    val reason: String,
)
