package com.on.network.dto.discussioncreate

import com.on.dialog.core.common.extension.formatToString
import com.on.model.discussion.draft.OnlineDiscussionDraft
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOnlineDiscussionRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("category")
    val category: String,
) {
    companion object {
        fun OnlineDiscussionDraft.toCreateRequest(): CreateOnlineDiscussionRequest = CreateOnlineDiscussionRequest(
            title = title,
            content = content,
            endDate = endDate.formatToString(),
            category = category,
        )
    }
}
