package com.on.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionEditRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("category")
    val category: String,
)
