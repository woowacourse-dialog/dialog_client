package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogHorizontalDivider
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel
import com.on.dialog.ui.component.ProfileImage
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.action_report
import dialog.feature.discussiondetail.impl.generated.resources.comment_add_opinion
import dialog.feature.discussiondetail.impl.generated.resources.comment_count_format
import dialog.feature.discussiondetail.impl.generated.resources.comment_delete_write
import dialog.feature.discussiondetail.impl.generated.resources.comment_edit_write
import dialog.feature.discussiondetail.impl.generated.resources.comment_reply_write
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CommentSection(
    comments: ImmutableList<DiscussionCommentUiModel>,
    totalCommentCount: Int,
    onCommentClick: () -> Unit,
    onReplyClick: (commentId: Long) -> Unit,
    onEditClick: (commentId: Long, content: String) -> Unit,
    onDeleteClick: (commentId: Long) -> Unit,
    onReportClick: (commentId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CommentSectionHeader(commentCount = totalCommentCount)

        CommentList(
            comments = comments,
            onReplyClick = onReplyClick,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            onReportClick = onReportClick,
        )

        DialogButton(
            text = stringResource(Res.string.comment_add_opinion),
            onClick = onCommentClick,
            style = DialogButtonStyle.Secondary,
            modifier = Modifier.fillMaxWidth().padding(horizontal = DialogTheme.spacing.large),
        ) {
            Icon(
                imageVector = DialogIcons.Add,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
    }
}

@Composable
private fun CommentSectionHeader(
    commentCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        DialogHorizontalDivider()

        Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))

        Text(
            text = stringResource(Res.string.comment_count_format, commentCount),
            style = DialogTheme.typography.titleLarge,
            modifier = Modifier.padding(start = DialogTheme.spacing.large),
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))

        DialogHorizontalDivider()
    }
}

@Composable
private fun CommentList(
    comments: ImmutableList<DiscussionCommentUiModel>,
    onReplyClick: (commentId: Long) -> Unit,
    onEditClick: (commentId: Long, content: String) -> Unit,
    onDeleteClick: (commentId: Long) -> Unit,
    onReportClick: (commentId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(DialogTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
    ) {
        comments.forEach { comment ->
            CommentItem(
                comment = comment,
                onReplyClick = { onReplyClick(comment.commentId) },
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                onReportClick = onReportClick,
            )
            DialogHorizontalDivider()
        }
    }
}

@Composable
private fun CommentHeader(
    comment: DiscussionCommentUiModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProfileImage(
                imageUrl = comment.authorAvatar.orEmpty(),
                modifier = Modifier.size(14.dp),
            )
            Spacer(modifier = Modifier.width(DialogTheme.spacing.extraSmall))
            Text(
                text = comment.authorName,
                style = DialogTheme.typography.titleSmall,
            )
        }

        Text(
            text = comment.formattedCreatedAt,
            style = DialogTheme.typography.labelSmall,
            color = DialogTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CommentItem(
    comment: DiscussionCommentUiModel,
    onReplyClick: (() -> Unit)?,
    onEditClick: (commentId: Long, content: String) -> Unit,
    onDeleteClick: (commentId: Long) -> Unit,
    onReportClick: (commentId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CommentHeader(comment = comment)

        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

        CommentMarkdown(
            content = comment.content,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.small))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            if (comment.isMine) {
                CommentSubButton(
                    text = stringResource(Res.string.comment_edit_write),
                    onClick = { onEditClick(comment.commentId, comment.content) },
                )

                CommentSubButton(
                    text = stringResource(Res.string.comment_delete_write),
                    textColor = DialogTheme.colorScheme.error,
                    onClick = { onDeleteClick(comment.commentId) },
                )
            } else {
                CommentSubButton(
                    text = stringResource(Res.string.action_report),
                    textColor = DialogTheme.colorScheme.error,
                    onClick = { onReportClick(comment.commentId) },
                )
            }

            onReplyClick?.let { onClick ->
                CommentSubButton(
                    text = stringResource(Res.string.comment_reply_write),
                    onClick = onClick,
                )
            }
        }

        comment.childComments.forEach { childComment ->
            Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
            ChildCommentItem(
                comment = childComment,
                onEditClick = { _, _ ->
                    onEditClick(
                        childComment.commentId,
                        childComment.content,
                    )
                },
                onDeleteClick = { onDeleteClick(childComment.commentId) },
                onReportClick = onReportClick,
            )
        }
    }
}

@Composable
private fun CommentSubButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = DialogTheme.colorScheme.primary,
) {
    Text(
        text = text,
        style = DialogTheme.typography.labelLarge,
        color = textColor,
        modifier = modifier
            .clip(DialogTheme.shapes.medium)
            .clickable(
                onClick = onClick,
                indication = ripple(),
                interactionSource = null,
            ).padding(DialogTheme.spacing.extraSmall),
    )
}

@Composable
private fun ChildCommentItem(
    comment: DiscussionCommentUiModel,
    onEditClick: (commentId: Long, content: String) -> Unit,
    onDeleteClick: (commentId: Long) -> Unit,
    onReportClick: (commentId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.width(DialogTheme.spacing.extraLarge))

        CommentItem(
            comment = comment,
            onReplyClick = null,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            onReportClick = onReportClick,
        )
    }
}

@ThemePreview
@Composable
private fun CommentSectionPreview() {
    DialogTheme {
        Surface {
            CommentSection(
                totalCommentCount = 6,
                comments = listOf(
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 2, 3),
                        commentId = 103L,
                        content = "실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.",
                        authorName = "taehoon",
                        authorAvatar = null,
                        isMine = true,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 2, 10),
                                commentId = 205L,
                                content = "경계가 애매하면 결국 이벤트버스/싱글톤이 슬금슬금 들어오죠… 🫠",
                                authorName = "minsu",
                                authorAvatar = null,
                                isMine = false,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 2, 16),
                                commentId = 206L,
                                content = "저도 그래서 상태는 Repository/Flow로 두고, 화면은 구독만 하게 만드는 쪽이 마음이 편하더라구요.",
                                authorName = "moondev",
                                authorAvatar = null,
                                isMine = true,
                            ),
                        ).toImmutableList(),
                    ),
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 3, 1),
                        commentId = 104L,
                        content = "한 가지 궁금한데요. 이 기능이 ‘일회성 이벤트’인지 ‘지속되는 도메인 상태’인지 먼저 정리하면 선택이 쉬울 것 같아요.",
                        authorName = "cream",
                        authorAvatar = null,
                        isMine = false,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 3, 6),
                                commentId = 207L,
                                content = "맞아요. 토스트/스낵바/네비 같은 건 이벤트 스트림, 북마크/스크랩은 상태 소스가 정석.",
                                authorName = "jerry",
                                authorAvatar = null,
                                isMine = false,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 3, 10),
                                commentId = 208L,
                                content = "이 기준 하나만 합의해도 PR 코멘트가 덜 아프게(?) 나오긴 합니다 ㅋㅋ",
                                authorName = "sora",
                                authorAvatar = null,
                                isMine = false,
                            ),
                        ).toImmutableList(),
                    ),
                ).toImmutableList(),
                onCommentClick = {},
                onReplyClick = {},
                onEditClick = { _, _ -> },
                onDeleteClick = {},
                onReportClick = {},
                modifier = Modifier.verticalScroll(rememberScrollState()),
            )
        }
    }
}
