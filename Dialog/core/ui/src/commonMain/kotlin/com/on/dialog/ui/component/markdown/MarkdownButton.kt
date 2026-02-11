package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogIconButtonTone
import com.on.dialog.ui.component.markdown.style.MarkdownStyle

/**
 * 마크다운 버튼들에 대한 style에 대한 apply처리의 책임을 위한 컴포넌트
 *
 * @param style 마크다운의 스타일 (예: 볼드, 이탤릭, 코드, 링크, 이미지, 인용 등)
 * @param content TextFieldValue로 표현된 마크다운 텍스트
 * @param onContentChanged 텍스트가 변경될 때 호출되는 콜백 함수
 */
@Composable
fun MarkdownButton(
    style: MarkdownStyle,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
) {
    DialogIconButton(
        onClick = {
            onContentChanged(style.transform(content))
        },
        content = {
            Icon(
                modifier = Modifier.fillMaxSize(0.6f),
                imageVector = style.icon,
                contentDescription = null,
            )
        },
        tone = DialogIconButtonTone.Primary,
    )
}
