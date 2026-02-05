package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
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
                modifier = Modifier.fillMaxSize(0.6f),
                imageVector = icon,
                contentDescription = null
            )
        },
        tone = DialogIconButtonTone.Primary
    )
}
