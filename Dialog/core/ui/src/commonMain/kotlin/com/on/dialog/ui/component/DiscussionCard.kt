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
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.ChipCategory
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogCardTone
import com.on.dialog.designsystem.component.DialogChipGroup
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.mapper.Category
import com.on.dialog.ui.mapper.toChipCategory
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.discussion_card_author
import dialog.core.ui.generated.resources.discussion_card_endAt
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DiscussionCard(
    chips: ImmutableList<ChipCategory>,
    onChipsChange: (ImmutableList<ChipCategory>) -> Unit,
    title: String,
    author: String,
    endAt: String,
    discussionCount: Int,
    modifier: Modifier = Modifier,
) {
    DialogCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        ) {
            Text(text = title, style = DialogTheme.typography.titleMedium)
            DiscussionCardFooter(author, endAt, discussionCount)
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
        Column(verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall)) {
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
        Category.ONLINE.toChipCategory(),
        Category.ANDROID.toChipCategory(),
        Category.FRONTEND.toChipCategory(),
    )

    Surface(color = DialogTheme.colorScheme.surfaceContainer) {
        DiscussionCard(
            chips = chips,
            onChipsChange = {},
            title = "MVVM 패턴 적용 시 ViewModel과 Repository 간 책임 분리 기준은?",
            author = "크림",
            endAt = "2026.01.31",
            discussionCount = 25,
            modifier = Modifier.padding(12.dp),
        )
    }
}
