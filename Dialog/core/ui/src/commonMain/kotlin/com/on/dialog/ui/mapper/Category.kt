package com.on.dialog.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.on.dialog.ui.component.ChipCategory
import com.on.model.common.Track
import com.on.model.discussion.content.DiscussionType
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.discussion_type_offline
import dialog.core.ui.generated.resources.discussion_type_online
import org.jetbrains.compose.resources.stringResource

@Composable
fun Track.toChipCategory(): ChipCategory = when (this) {
    Track.ANDROID -> ChipCategory(
        text = stringResource(resource = this.toStringResource()),
        textColor = Color(color = 0xFF003D2E),
        backgroundColor = Color(color = 0xFF3DDC84),
    )

    Track.BACKEND -> ChipCategory(
        text = stringResource(resource = this.toStringResource()),
        textColor = Color.White,
        backgroundColor = Color(color = 0xFFFF6F00),
    )

    Track.FRONTEND -> ChipCategory(
        text = stringResource(resource = this.toStringResource()),
        textColor = Color.White,
        backgroundColor = Color(color = 0xFF2196F3),
    )
}

@Composable
fun DiscussionType.toChipCategory(): ChipCategory = when (this) {
    DiscussionType.ONLINE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_type_online),
        textColor = Color.White,
        backgroundColor = Color(color = 0xFF000000),
    )

    DiscussionType.OFFLINE -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_type_offline),
        textColor = Color.White,
        backgroundColor = Color(color = 0xFF000000),
    )

    DiscussionType.UNDEFINED -> ChipCategory(
        text = stringResource(resource = Res.string.discussion_type_online),
        textColor = Color.White,
        backgroundColor = Color(color = 0xFF000000),
    )
}
