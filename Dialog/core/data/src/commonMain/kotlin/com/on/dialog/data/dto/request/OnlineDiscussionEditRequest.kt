package com.on.dialog.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionEditRequest(
    @SerialName("category")
    val category: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("endDate")
    val endDate: String?,
    @SerialName("title")
    val title: String?,
)
