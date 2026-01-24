package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.mapper.toChipCategory
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.discussion_card_author
import dialog.core.ui.generated.resources.discussion_card_endAt
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscussionCard(
    chips: ImmutableList<ChipCategory>,
    onChipsChange: (List<ChipCategory>) -> Unit,
    title: String,
    author: String,
    endAt: String,
    discussionCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    DialogCard(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        ) {
            DialogChipGroup(chips = chips, onChipsChange = onChipsChange)
            Text(text = title, style = DialogTheme.typography.titleMedium)
            DiscussionCardFooter(author = author, endAt = endAt, discussionCount = discussionCount)
        }
    }
}

@Composable
private fun DiscussionCardFooter(
    author: String,
    endAt: String,
    discussionCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.extraSmall)) {
            Text(
                text = stringResource(Res.string.discussion_card_author, author),
                style = DialogTheme.typography.bodyMedium,
            )
            Text(
                text = stringResource(Res.string.discussion_card_endAt, endAt),
                style = DialogTheme.typography.bodyMedium,
            )
        }
        InteractionIndicator(
            icon = DialogIcons.Chat,
            iconTint = LocalContentColor.current,
            count = discussionCount,
        )
    }
}

@Preview
@Composable
private fun DiscussionCardPreviewLight() {
    DialogTheme {
        DiscussionCardPreviewContent()
    }
}

@Preview
@Composable
private fun DiscussionCardPreviewDark() {
    DialogTheme(darkTheme = true) {
        DiscussionCardPreviewContent()
    }
}

@Composable
private fun DiscussionCardPreviewContent() {
    val chips: ImmutableList<ChipCategory> = persistentListOf(
        DiscussionType.ONLINE.toChipCategory(),
        Track.ANDROID.toChipCategory(),
        Track.FRONTEND.toChipCategory(),
    )

    Surface(color = DialogTheme.colorScheme.surfaceContainer) {
        DiscussionCard(
            chips = chips,
            onChipsChange = {},
            title = "MVVM 패턴 적용 시 ViewModel과 Repository 간 책임 분리 기준은?",
            author = "크림",
            endAt = "2026.01.31",
            discussionCount = 25,
            modifier = Modifier.padding(all = 12.dp),
            onClick = {},
        )
    }
}
