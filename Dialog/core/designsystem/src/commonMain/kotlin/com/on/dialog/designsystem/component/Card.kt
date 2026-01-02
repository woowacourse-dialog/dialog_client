package com.on.dialog.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.ShadowLevel
import com.on.dialog.designsystem.theme.dropShadow
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class DialogCardTone { Primary, Secondary, White }

/**
 * 그림자와 둥근 모서리를 가진 카드 컴포넌트입니다.
 *
 * @param modifier 카드 전체에 적용할 Modifier.
 * @param tone 배경색과 리플 컬러를 결정하는 톤.
 * @param contentPadding 카드 내부 여백.
 * @param onClick null이 아니면 클릭 가능한 카드로 동작합니다.
 * @param content 카드 본문 콘텐츠 슬롯.
 */
@Composable
fun DialogCard(
    modifier: Modifier = Modifier,
    tone: DialogCardTone = DialogCardTone.Secondary,
    contentPadding: PaddingValues = PaddingValues(DialogTheme.spacing.medium),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val background =
        when (tone) {
            DialogCardTone.Primary -> DialogTheme.colorScheme.primary
            DialogCardTone.Secondary -> DialogTheme.colorScheme.secondary
            DialogCardTone.White -> Color.White
        }

    Box(
        modifier =
            modifier
                .dropShadow(level = ShadowLevel.SMALL)
                .clip(shape = DialogTheme.shapes.medium)
                .background(color = background)
                .clickableCard(
                    enabled = onClick != null,
                    tone = tone,
                ) { onClick?.invoke() }
                .padding(contentPadding),
        content = content,
    )
}

@Composable
private fun Modifier.clickableCard(
    enabled: Boolean,
    tone: DialogCardTone,
    onClick: () -> Unit,
): Modifier =
    this.clickable(
        onClick = onClick,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication =
            ripple(
                bounded = true,
                color =
                    when (tone) {
                        DialogCardTone.Primary -> DialogTheme.colorScheme.primary
                        DialogCardTone.Secondary -> DialogTheme.colorScheme.secondary
                        DialogCardTone.White -> Color.White
                    },
            ),
    )

@Preview(showBackground = true)
@Composable
private fun DialogCardPreviewLight() {
    DialogTheme {
        DialogCardPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogCardPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogCardPreviewContent()
    }
}

@Composable
private fun DialogCardPreviewContent() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DialogCard(tone = DialogCardTone.Primary, onClick = {}) {
            Text("Primary Card", color = Color.White)
        }
        DialogCard(tone = DialogCardTone.Secondary, onClick = {}) {
            Text("Secondary Card")
        }
        DialogCard(tone = DialogCardTone.White, onClick = {}) {
            Text("White Card")
        }
    }
}
