package com.on.network.dto.discussionedit

import com.on.dialog.core.common.extension.formatToString
import com.on.model.discussion.draft.OfflineDiscussionDraft
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionEditRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("place")
    val place: String,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerialName("category")
    val category: String,
) {
    fun OfflineDiscussionDraft.toDto(): OfflineDiscussionEditRequest =
        OfflineDiscussionEditRequest(
            title = title,
            content = content,
            startAt = startAt.formatToString(),
            endAt = endAt.formatToString(),
            place = place,
            maxParticipantCount = maxParticipantCount,
            category = category,
        )
}
