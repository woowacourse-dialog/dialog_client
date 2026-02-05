package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import dialog.core.ui.generated.resources.discussion_card_period
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscussionCard(
    chips: ImmutableList<ChipCategory>,
    title: String,
    author: String,
    period: String,
    discussionCount: Int,
    modifier: Modifier = Modifier,
    participant: String? = null,
    onClick: () -> Unit,
) {
    DialogCard(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        ) {
            Row {
                DialogChipGroup(chips = chips, onChipsChange = {}, modifier = Modifier.weight(1f))
                participant?.let { text -> DiscussionCardParticipant(participant = text) }
            }
            Text(text = title, style = DialogTheme.typography.titleMedium)
            DiscussionCardFooter(author = author, period = period)
        }

        InteractionIndicator(
            icon = DialogIcons.Chat,
            iconTint = LocalContentColor.current,
            count = discussionCount,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun DiscussionCardParticipant(
    participant: String,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalContentColor provides DialogTheme.colorScheme.onSurfaceVariant,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = DialogIcons.Group,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(DialogTheme.spacing.extraSmall))
            Text(
                text = participant,
                style = DialogTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun DiscussionCardFooter(
    author: String,
    period: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
    ) {
        ProvideTextStyle(
            value = DialogTheme.typography.bodySmall,
        ) {
            Text(text = stringResource(Res.string.discussion_card_author, author))
            Text(text = stringResource(Res.string.discussion_card_period, period))
        }
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
            title = "MVVM 패턴 적용 시 ViewModel과 Repository 간 책임 분리 기준은?",
            author = "크림",
            period = "~ 2026.01.01",
            discussionCount = 25,
            participant = "2/4",
            modifier = Modifier.padding(all = 12.dp),
            onClick = {},
        )
    }
}
