package com.on.dialog.feature.discussionlist.impl.model

import com.on.dialog.model.discussion.content.DiscussionStatus

internal enum class DiscussionStatusUiModel(
    val title: String,
) {
    RECRUITING(title = "모집 중"),
    RECRUIT_COMPLETE(title = "모집 완료"),
    INDISCUSSION(title = "토론 중"),
    DISCUSSION_COMPLETE(title = "토론 완료"),
    ;

    fun toDomain(): DiscussionStatus = when (this) {
        RECRUITING -> DiscussionStatus.RECRUITING
        RECRUIT_COMPLETE -> DiscussionStatus.RECRUIT_COMPLETE
        INDISCUSSION -> DiscussionStatus.IN_DISCUSSION
        DISCUSSION_COMPLETE -> DiscussionStatus.DISCUSSION_COMPLETE
    }

    companion object {
        fun DiscussionStatus.toUiModel(): DiscussionStatusUiModel = when (this) {
            DiscussionStatus.RECRUITING -> RECRUITING
            DiscussionStatus.RECRUIT_COMPLETE -> RECRUIT_COMPLETE
            DiscussionStatus.IN_DISCUSSION -> INDISCUSSION
            DiscussionStatus.DISCUSSION_COMPLETE -> DISCUSSION_COMPLETE
        }
    }
}
