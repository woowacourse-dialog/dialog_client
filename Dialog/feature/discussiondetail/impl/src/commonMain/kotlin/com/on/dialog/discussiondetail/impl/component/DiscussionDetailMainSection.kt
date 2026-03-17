package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.util.drawFadingEdges
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun DiscussionDetailMainSection(
    discussion: DiscussionDetailUiModel?,
    comments: ImmutableList<DiscussionCommentUiModel>,
    totalCommentCount: Int,
    isMyDiscussion: Boolean,
    isShowParticipateButton: Boolean,
    isShowSummary: Boolean,
    isGeneratingSummary: Boolean,
    onParticipateClick: () -> Unit,
    onSummaryClick: () -> Unit,
    onCommentClick: () -> Unit,
    onReplyClick: (commentId: Long) -> Unit,
    onCommentEditClick: (commentId: Long, content: String) -> Unit,
    onCommentDeleteClick: (commentId: Long) -> Unit,
    onCommentReportClick: (commentId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawFadingEdges(scrollState)
            .verticalScroll(scrollState)
            .padding(
                horizontal = DialogTheme.spacing.large,
                vertical = DialogTheme.spacing.medium,
            ),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
    ) {
        discussion?.let { detail ->
            DialogCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DiscussionHeaderSection(discussion = detail)
            }

            DialogCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(DialogTheme.spacing.none),
            ) {
                DiscussionBodySection(
                    discussion = detail,
                    isShowParticipateButton = isShowParticipateButton,
                    onParticipateClick = onParticipateClick,
                )
            }

            if (isShowSummary && detail is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel) {
                DiscussionSummarySection(
                    summary = detail.summary,
                    isMyDiscussion = isMyDiscussion,
                    isGeneratingSummary = isGeneratingSummary,
                    onSummaryClick = onSummaryClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        DialogCard(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(DialogTheme.spacing.none),
        ) {
            DiscussionCommentSection(
                comments = comments,
                totalCommentCount = totalCommentCount,
                onCommentClick = onCommentClick,
                onReplyClick = onReplyClick,
                onEditClick = onCommentEditClick,
                onDeleteClick = onCommentDeleteClick,
                onReportClick = onCommentReportClick,
            )
        }
    }
}
