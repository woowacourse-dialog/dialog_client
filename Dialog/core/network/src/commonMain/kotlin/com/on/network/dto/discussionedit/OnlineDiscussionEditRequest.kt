package com.on.network.dto.discussionedit

import com.on.dialog.core.common.extension.formatToString
import com.on.model.discussion.draft.OnlineDiscussionDraft
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
) {
    fun OnlineDiscussionDraft.toDto(): OnlineDiscussionEditRequest =
        OnlineDiscussionEditRequest(
            title = title,
            content = content,
            endDate = endDate.formatToString(),
            category = category,
        )
}
