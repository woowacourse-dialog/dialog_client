package com.on.dialog.scrap.impl.model

import androidx.compose.runtime.Composable
import com.on.dialog.model.discussion.content.DiscussionStatus
import dialog.feature.scrap.impl.generated.resources.Res
import dialog.feature.scrap.impl.generated.resources.discussion_status_discussion_complete
import dialog.feature.scrap.impl.generated.resources.discussion_status_in_discussion
import dialog.feature.scrap.impl.generated.resources.discussion_status_recruit_complete
import dialog.feature.scrap.impl.generated.resources.discussion_status_recruiting
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal enum class DiscussionStatusUiModel(
    private val titleRes: StringResource,
) {
    RECRUITING(Res.string.discussion_status_recruiting),
    RECRUIT_COMPLETE(Res.string.discussion_status_recruit_complete),
    IN_DISCUSSION(Res.string.discussion_status_in_discussion),
    DISCUSSION_COMPLETE(Res.string.discussion_status_discussion_complete),
    ;

    val title: String
        @Composable get() = stringResource(titleRes)

    companion object {
        fun DiscussionStatus.toUiModel(): DiscussionStatusUiModel = when (this) {
            DiscussionStatus.RECRUITING -> RECRUITING
            DiscussionStatus.RECRUIT_COMPLETE -> RECRUIT_COMPLETE
            DiscussionStatus.IN_DISCUSSION -> IN_DISCUSSION
            DiscussionStatus.DISCUSSION_COMPLETE -> DISCUSSION_COMPLETE
        }
    }
}
