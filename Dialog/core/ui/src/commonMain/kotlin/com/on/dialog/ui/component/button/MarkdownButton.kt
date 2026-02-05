package com.on.dialog.ui.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogIconButtonTone

@Composable
fun MarkdownButton(
    style: MarkdownStyle,
    icon: ImageVector,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
) {
    DialogIconButton(
        onClick = {
            style.apply(
                content = content,
                onContentChanged = onContentChanged
            )
        },
        content = {
            Icon(
                // TODO 버튼 크게 맞게 사이즈 넣기
                modifier = Modifier.size(30.dp),
                imageVector = icon,
                contentDescription = null
            )
        },
        tone = DialogIconButtonTone.Primary
    )
}
