package com.on.dialog.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.component.ChipCategory
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.discussion_status_discussion_complete
import dialog.core.ui.generated.resources.discussion_status_in_discussion
import dialog.core.ui.generated.resources.discussion_status_recruit_complete
import dialog.core.ui.generated.resources.discussion_status_recruiting
import dialog.core.ui.generated.resources.discussion_type_offline
import dialog.core.ui.generated.resources.discussion_type_online
import dialog.core.ui.generated.resources.track_android
import dialog.core.ui.generated.resources.track_backend
import dialog.core.ui.generated.resources.track_common
import dialog.core.ui.generated.resources.track_frontend
import org.jetbrains.compose.resources.stringResource

@Composable
fun Track.toChipCategory(): ChipCategory = when (this) {
    Track.ANDROID -> ChipCategory(
        text = stringResource(resource = Res.string.track_android),
        textColor = Color(color = 0xFF5E35B1),
        backgroundColor = Color(color = 0xFFEDE7F6),
    )

    Track.BACKEND -> ChipCategory(
        text = stringResource(resource = Res.string.track_backend),
        textColor = Color(color = 0xFF3D5A80),
        backgroundColor = Color(color = 0xFFE8EDF4),
    )

    Track.FRONTEND -> ChipCategory(
        text = stringResource(resource = Res.string.track_frontend),
        textColor = Color(color = 0xFF00796B),
        backgroundColor = Color(color = 0xFFE0F2F1),
    )

    Track.COMMON -> ChipCategory(
        text = stringResource(resource = Res.string.track_common),
        textColor = Color(color = 0xFF546E7A),
        backgroundColor = Color(color = 0xFFF1F4F8),
    )
}

@Composable
fun DiscussionType.toChipCategory(): ChipCategory = when (this) {
    DiscussionType.ONLINE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_type_online),
        textColor = Color(color = 0xFF42A5F5),
        backgroundColor = Color(color = 0xFFE3F2FD),
    )

    DiscussionType.OFFLINE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_type_offline),
        textColor = Color(color = 0xFFFB8C00),
        backgroundColor = Color(color = 0xFFFFF3E0),
    )
}

@Composable
fun DiscussionStatus.toChipCategory(): ChipCategory = when (this) {
    DiscussionStatus.RECRUITING -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_status_recruiting),
        textColor = Color(color = 0xFF00796B),
        backgroundColor = Color(color = 0xFFE0F2F1),
    )

    DiscussionStatus.RECRUIT_COMPLETE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_status_recruit_complete),
        textColor = Color(color = 0xFFE65100),
        backgroundColor = Color(color = 0xFFFFF3E0),
    )

    DiscussionStatus.IN_DISCUSSION -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_status_in_discussion),
        textColor = Color(color = 0xFF1565C0),
        backgroundColor = Color(color = 0xFFE3F2FD),
    )

    DiscussionStatus.DISCUSSION_COMPLETE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_status_discussion_complete),
        textColor = Color(color = 0xFF78909C),
        backgroundColor = Color(color = 0xFFF1F4F8),
    )
}
