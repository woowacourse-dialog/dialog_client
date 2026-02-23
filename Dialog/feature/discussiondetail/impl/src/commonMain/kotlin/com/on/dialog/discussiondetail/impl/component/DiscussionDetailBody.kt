package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.DiscussionType
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.participate_button
import dialog.feature.discussiondetail.impl.generated.resources.participate_done_button
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailBody(
    discussion: DiscussionDetailUiModel,
    isMyDiscussion: Boolean,
    isParticipating: Boolean,
    onSummaryClick: () -> Unit,
    onParticipateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraLarge),
    ) {
        Markdown(
            content = discussion.detailContent.content,
            colors = markdownColor(),
            typography = markdownTypography(),
            modifier = Modifier.fillMaxWidth(),
        )
        DiscussionSummary(
            summary = discussion.summary,
            isMyDiscussion = isMyDiscussion,
            onSummaryClick = onSummaryClick,
        )
        if (discussion.discussionType == DiscussionType.OFFLINE) {
            ParticipateButton(
                isParticipating = isParticipating,
                onParticipateClick = onParticipateClick,
            )
        }
    }
}

@Composable
private fun ParticipateButton(
    isParticipating: Boolean,
    onParticipateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogButton(
        text = stringResource(if (isParticipating) Res.string.participate_done_button else Res.string.participate_button),
        onClick = onParticipateClick,
        enabled = !isParticipating,
        modifier = modifier.fillMaxWidth(),
    ) {
        Icon(imageVector = DialogIcons.Group, contentDescription = null)
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailBodyPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailBody(
                discussion = DiscussionDetailUiModel.OfflineDiscussionDetailUiModel(
                    detailContent = DetailContentUiModel(
                        id = 0L,
                        title = "다이얼로그는 무슨 뜻인가요?",
                        author = AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                        createdAt = "2023.03.01",
                        likeCount = 100,
                        modifiedAt = "2023.03.01",
                    ),
                    summary = "요약된 내용",
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    participantCapacity = "1/4",
                    place = "우아한테크코스",
                    participants = persistentListOf(
                        ParticipantUiModel(id = 0L, name = "다이스"),
                        ParticipantUiModel(id = 1L, name = "제리"),
                        ParticipantUiModel(id = 2L, name = "크림"),
                    ),
                    dateTimePeriod = "2023년 3월 1일 13시 ~ 15시",
                ),
                isMyDiscussion = true,
                isParticipating = false,
                onSummaryClick = {},
                onParticipateClick = {},
            )
        }
    }
}
