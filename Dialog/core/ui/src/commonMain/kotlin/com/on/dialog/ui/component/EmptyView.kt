package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme

@Immutable
data class EmptyAction(
    val label: String,
    val onClick: () -> Unit,
)

@Composable
fun CommonEmptyView(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector? = null,
    primaryAction: EmptyAction? = null,
    secondaryAction: EmptyAction? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 360.dp),
        )

        if (description.isNullOrBlank().not()) {
            Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 360.dp),
            )
        }

        Spacer(modifier = Modifier.height(DialogTheme.spacing.large))

        if (primaryAction != null || secondaryAction != null) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            ) {
                primaryAction?.let { action ->
                    DialogButton(
                        text = action.label,
                        onClick = action.onClick,
                        style = DialogButtonStyle.Primary,
                    )
                }

                secondaryAction?.let { action ->
                    DialogButton(
                        text = action.label,
                        onClick = action.onClick,
                        style = DialogButtonStyle.Secondary,
                    )
                }
            }
        }
    }
}

@ThemePreview
@Composable
private fun CommonEmptyViewPreview() {
    DialogTheme {
        Scaffold {
            CommonEmptyView(
                title = "아직 개설한 토론이 없어요",
                description = "첫 토론을 만들어보세요",
                icon = DialogIcons.Empty,
                primaryAction = EmptyAction(label = "토론 만들기", onClick = {}),
                secondaryAction = EmptyAction(label = "새로고침", onClick = {}),
            )
        }
    }
}
