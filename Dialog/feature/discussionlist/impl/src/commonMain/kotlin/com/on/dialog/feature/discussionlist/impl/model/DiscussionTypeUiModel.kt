package com.on.dialog.feature.discussionlist.impl.model

import androidx.compose.runtime.Composable
import com.on.dialog.model.discussion.content.DiscussionType
import dialog.feature.discussionlist.impl.generated.resources.Res
import dialog.feature.discussionlist.impl.generated.resources.discussion_type_offline
import dialog.feature.discussionlist.impl.generated.resources.discussion_type_online
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal enum class DiscussionTypeUiModel(
    val titleResId: StringResource,
) {
    ONLINE(titleResId = Res.string.discussion_type_online),
    OFFLINE(titleResId = Res.string.discussion_type_offline),
    ;

    val title: String
        @Composable get() = stringResource(titleResId)

    fun toDomain(): DiscussionType = when (this) {
        ONLINE -> DiscussionType.ONLINE
        OFFLINE -> DiscussionType.OFFLINE
    }
}
