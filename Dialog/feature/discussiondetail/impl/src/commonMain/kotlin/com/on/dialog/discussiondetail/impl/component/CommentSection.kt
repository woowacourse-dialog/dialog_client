package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogHorizontalDivider
import com.on.dialog.designsystem.component.DialogVerticalDivider
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel
import com.on.dialog.ui.component.ProfileImage
import com.on.dialog.ui.component.markdown.DialogMarkdown
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime

@Composable
internal fun CommentSection(
    comments: ImmutableList<DiscussionCommentUiModel>,
    onCommentInputClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CommentSectionHeader(commentCount = comments.size)

        CommentList(comments = comments)

        CommentInputPlaceholder(
            text = "",
            onClick = onCommentInputClick,
        )
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
            text = "댓글 ${commentCount}개",
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(DialogTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
    ) {
        comments.forEach { comment ->
            CommentItem(comment = comment)
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
                contentDescription = "",
                modifier = Modifier.size(12.dp),
            )
            Spacer(modifier = Modifier.width(DialogTheme.spacing.extraSmall))
            Text(
                text = comment.authorName,
                style = DialogTheme.typography.titleSmall,
            )
        }

        Text(
            text = comment.formatedCreatedAt,
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
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CommentHeader(comment = comment)

        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

        DialogMarkdown(
            content = comment.content,
            modifier = Modifier.fillMaxWidth(),
        )

        comment.childComments.forEach { childComment ->
            Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
            ChildCommentItem(comment = childComment)
        }
    }
}

@Composable
private fun ChildCommentItem(
    comment: DiscussionCommentUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Spacer(modifier = Modifier.width(DialogTheme.spacing.medium))

        DialogVerticalDivider(thickness = 2.dp)

        Spacer(modifier = Modifier.width(DialogTheme.spacing.medium))

        Column(modifier = Modifier.fillMaxWidth()) {
            CommentHeader(comment = comment)

            Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

            DialogMarkdown(content = comment.content)
        }
    }
}

@ThemePreview
@Composable
private fun CommentSectionPreview() {
    DialogTheme {
        Surface {
            CommentSection(
                comments = listOf(
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 2, 23, 58),
                        commentId = 101L,
                        content = "저는 이 주제에서 ‘합의 가능한 정의’를 먼저 잡는 게 핵심이라고 봐요. 용어가 흐리면 결론이 계속 흔들리더라고요.",
                        authorName = "문장훈",
                        authorAvatar = null,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 0, 6),
                                commentId = 201L,
                                content = "완전 공감… 정의 먼저 안 잡고 달리면 서로 다른 걸 논의하게 되는 느낌 😅",
                                authorName = "jerry",
                                authorAvatar = null,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 0, 14),
                                commentId = 202L,
                                content = "그래서 저는 ‘정의/범위/예외’ 3가지만 체크하고 시작해요. 그 뒤에 의견 모으는 게 훨씬 빨라요.",
                                authorName = "sora",
                                authorAvatar = null,
                            ),
                        ).toImmutableList(),
                    ),
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 1, 12),
                        commentId = 102L,
                        content = "의견 타입(찬성/반대/질문/보완)을 고르면 스레드 읽기가 훨씬 편해질 것 같아요. 특히 질문만 모아보기가 되면 좋겠어요.",
                        authorName = "moondev03",
                        authorAvatar = null,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 1, 18),
                                commentId = 203L,
                                content = "질문만 보기 + 답변 달리면 자동으로 ‘해결됨’ 토글 되는 것도 좋아요.",
                                authorName = "hana",
                                authorAvatar = null,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 1, 25),
                                commentId = 204L,
                                content = "질문 탭에선 최신순/추천순 정렬도 있으면 깔끔할 듯!",
                                authorName = "jerry",
                                authorAvatar = null,
                            ),
                        ).toImmutableList(),
                    ),
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 2, 3),
                        commentId = 103L,
                        content = "실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.",
                        authorName = "taehoon",
                        authorAvatar = null,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 2, 10),
                                commentId = 205L,
                                content = "경계가 애매하면 결국 이벤트버스/싱글톤이 슬금슬금 들어오죠… 🫠",
                                authorName = "minsu",
                                authorAvatar = null,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 2, 16),
                                commentId = 206L,
                                content = "저도 그래서 상태는 Repository/Flow로 두고, 화면은 구독만 하게 만드는 쪽이 마음이 편하더라구요.",
                                authorName = "문장훈",
                                authorAvatar = null,
                            ),
                        ).toImmutableList(),
                    ),
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 3, 1),
                        commentId = 104L,
                        content = "한 가지 궁금한데요. 이 기능이 ‘일회성 이벤트’인지 ‘지속되는 도메인 상태’인지 먼저 정리하면 선택이 쉬울 것 같아요.",
                        authorName = "yujin",
                        authorAvatar = null,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 3, 6),
                                commentId = 207L,
                                content = "맞아요. 토스트/스낵바/네비 같은 건 이벤트 스트림, 북마크/스크랩은 상태 소스가 정석.",
                                authorName = "jerry",
                                authorAvatar = null,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 3, 10),
                                commentId = 208L,
                                content = "이 기준 하나만 합의해도 PR 코멘트가 덜 아프게(?) 나오긴 합니다 ㅋㅋ",
                                authorName = "sora",
                                authorAvatar = null,
                            ),
                        ).toImmutableList(),
                    ),
                    DiscussionCommentUiModel(
                        createdAt = LocalDateTime(2026, 3, 3, 4, 20),
                        commentId = 105L,
                        content = "개인적으로는 ‘토론 생성 허들 낮추기’가 가장 큰 임팩트 같아요. 주제 추천 + 템플릿만 있어도 참여율이 확 올라갈 듯.",
                        authorName = "daisy",
                        authorAvatar = null,
                        childComments = listOf(
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 4, 28),
                                commentId = 209L,
                                content = "주제 추천에 ‘난이도/소요시간/전제지식’ 표시도 있으면 더 좋겠네요!",
                                authorName = "hana",
                                authorAvatar = null,
                            ),
                            DiscussionCommentUiModel(
                                createdAt = LocalDateTime(2026, 3, 3, 4, 33),
                                commentId = 210L,
                                content = "그리고 주제 클릭하면 바로 토론방 생성까지 이어지면 진짜 편할 듯 🙌",
                                authorName = "moondev03",
                                authorAvatar = null,
                            ),
                        ).toImmutableList(),
                    ),
                ).toImmutableList(),
                onCommentInputClick = {},
            )
        }
    }
}
