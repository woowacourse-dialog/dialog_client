package com.on.dialog.feature.discussionlist.impl.model

import com.on.dialog.model.discussion.content.DiscussionType

internal enum class DiscussionTypeUiModel(
    val title: String,
) {
    ONLINE(title = "온라인"),
    OFFLINE(title = "오프라인"),
    ;

    fun toDomain(): DiscussionType = when (this) {
        ONLINE -> DiscussionType.ONLINE
        OFFLINE -> DiscussionType.OFFLINE
    }

    companion object {
        fun DiscussionType.toUiModel(): DiscussionTypeUiModel = when (this) {
            DiscussionType.ONLINE -> ONLINE
            DiscussionType.OFFLINE -> OFFLINE
        }
    }
}
