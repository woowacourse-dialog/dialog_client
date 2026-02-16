package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
internal fun DiscussionSummary(
    summary: String?,
    modifier: Modifier = Modifier
) {
    DialogCard(modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small)) {
            IconTextRow(iconImage = DialogIcons.AutoAwesome, text = "토론 요약")

            if (summary == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = DialogTheme.spacing.medium)
                ) {
                    Text(
                        text = "토론이 모두 종료되었을 때 요약을 생성해주세요.",
                        style = DialogTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "요약은 1회만 생성할 수 있어요.",
                        style = DialogTheme.typography.bodyMedium
                    )
                    DialogButton(text = "AI 요약 생성하기", onClick = {})
                }
            } else {
                Markdown(
                    content = summary,
                    colors = markdownColor(),
                    typography = markdownTypography(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DiscussionSummaryEmptyPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        DiscussionSummary(summary = null, modifier = modifier)
    }
}

@Preview

@Composable
private fun DiscussionSummaryPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        DiscussionSummary(
            summary = """
    1. **토론의 핵심 주제**
    
    - 이 토론은 Koin의 기능과 역할에 대한 논의
    
    2. **참여자별 입장 비교**
   
    | 참여자 | 주요 주장 | 근거 요약 |
    | --- | --- | --- |
    | 크림 | Koin은 Service Locator이다 | Koin |
        """.trimIndent()
        )
    }
}