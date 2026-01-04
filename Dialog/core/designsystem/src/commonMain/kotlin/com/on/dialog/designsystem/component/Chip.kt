package com.on.dialog.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DialogChip(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = DialogTheme.colorScheme.primary,
    onRemove: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = DialogTheme.shapes.large,
        color = backgroundColor,
        contentColor = textColor,
        tonalElevation = 0.dp,
    ) {
        Row(
            modifier =
                Modifier.padding(
                    horizontal = DialogTheme.spacing.small,
                    vertical = DialogTheme.spacing.small,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = DialogTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
            )
            if (onRemove != null) {
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "삭제",
                    modifier =
                        Modifier
                            .size(12.dp)
                            .clip(DialogTheme.shapes.medium)
                            .clickable { onRemove() },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogChipPreview() {
    DialogTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DialogChip(
                text = "Android",
                textColor = Color(0xFF003D2E),
                backgroundColor = Color(0xFF3DDC84),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(
                text = "Backend",
                textColor = Color.White,
                backgroundColor = Color(0xFFFF6F00),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(
                text = "Frontend",
                textColor = Color.White,
                backgroundColor = Color(0xFF2196F3),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(text = "#기본 스타일")
            Spacer(Modifier.height(8.dp))
            DialogChip(text = "#기본 스타일", onRemove = {})
        }
    }
}
