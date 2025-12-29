package com.on.dialog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SummaryRequest(
    @SerialName("discussionId")
    val discussionId: Int,
)
