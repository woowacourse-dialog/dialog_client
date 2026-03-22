package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownDivider
import com.mikepenz.markdown.compose.elements.MarkdownHeader
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import org.intellij.markdown.MarkdownTokenTypes

@Composable
fun DialogMarkdown(
    content: String,
    modifier: Modifier = Modifier,
) {
    val colors: ColorScheme = DialogTheme.colorScheme
    val typography: Typography = DialogTheme.typography

    Markdown(
        content = content,
        colors = markdownColor(
            text = colors.onSurface,
            codeBackground = colors.surfaceVariant,
            inlineCodeBackground = colors.surfaceVariant,
            dividerColor = colors.outlineVariant,
            tableBackground = colors.surfaceContainerLow,
        ),
        // 제목
        components = markdownComponents(
            heading1 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.headlineMedium)
                MarkdownDivider(modifier = Modifier.padding(vertical = DialogTheme.spacing.medium))
            },
            heading2 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.headlineSmall)
                MarkdownDivider(modifier = Modifier.padding(vertical = DialogTheme.spacing.medium))
            },
            heading3 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.titleLarge)
            },
            heading4 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.titleLarge)
            },
            heading5 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.titleLarge)
            },
            heading6 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(it.content, it.node, style = typography.titleLarge)
            },
            setextHeading1 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(
                    content = it.content,
                    node = it.node,
                    style = typography.headlineMedium,
                    contentChildType = MarkdownTokenTypes.SETEXT_CONTENT,
                )
                MarkdownDivider(modifier = Modifier.padding(vertical = DialogTheme.spacing.medium))
            },
            setextHeading2 = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
                MarkdownHeader(
                    content = it.content,
                    node = it.node,
                    style = typography.headlineSmall,
                    contentChildType = MarkdownTokenTypes.SETEXT_CONTENT,
                )
                MarkdownDivider(modifier = Modifier.padding(vertical = DialogTheme.spacing.medium))
            },
            eol = {
                Spacer(modifier = Modifier.height(DialogTheme.spacing.small))
            },
        ),
        typography = markdownTypography(
            // 일반 텍스트
            text = typography.bodyLarge,
            paragraph = typography.bodyLarge,
            // 코드, 인라인 코드
            code = typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit.Unspecified,
            ),
            inlineCode = typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit.Unspecified,
            ),
            // 인용문
            quote = typography.bodyLarge.copy(
                color = colors.onSurfaceVariant.copy(alpha = 0.6f),
                fontStyle = FontStyle.Italic,
            ),
            // 번호, 글머리 기호
            ordered = typography.bodyLarge,
            bullet = typography.bodyLarge,
            list = typography.bodyLarge,
            // 링크
            textLink = TextLinkStyles(
                style = typography.bodyLarge
                    .copy(
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline,
                        color = DialogTheme.colors.blue,
                    ).toSpanStyle(),
                pressedStyle = typography.bodyLarge
                    .copy(
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline,
                        color = DialogTheme.colors.blue.copy(alpha = 0.6f),
                    ).toSpanStyle(),
            ),
            // 표
            table = typography.bodyMedium,
        ),
        modifier = modifier,
    )
}

@Composable
@ThemePreview
private fun DialogMarkdownDarkPreview() {
    DialogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = DialogTheme.colorScheme.surface,
        ) {
            DialogMarkdown(
                content = PREVIEW_MARKDOWN.trimIndent(),
                modifier = Modifier.padding(DialogTheme.spacing.medium),
            )
        }
    }
}

private const val PREVIEW_MARKDOWN = """
    # H1 제목
    ## H2 제목
    ### H3 제목
    #### H4 제목
    ##### H5 제목
    ###### H6 제목
    
    일반 문단(Paragraph)입니다.  
    **굵게**, *기울임*, ~~취소선~~, 그리고 [링크 예시](https://example.com) 입니다.
    
    인라인 코드: `val a = 1`
    
    > 인용문(Quote)입니다. 여러 줄도 가능합니다.  
    > 두 번째 줄입니다.
    

    ---
    
    - Bullet 1
    - Bullet 2
      - Nested Bullet
    1. Ordered 1
    2. Ordered 2
       1. Nested Ordered
    
    ```kotlin
    data class User(val id: Long, val name: String)
    
    fun main() {
        println("Hello Markdown")
    }
    ```
    
    | 이름 | 나이 | 직업 |
    |------|------|------|
    | 철수 | 25 | 개발자 |
    | 영희 | 27 | 디자이너 |
    """
