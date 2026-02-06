package com.on.dialog.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
fun AnimatedTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedBackgroundColor: Color = DialogTheme.colorScheme.primary,
    unselectedBackgroundColor: Color = DialogTheme.colorScheme.surface,
    selectedContentColor: Color = DialogTheme.colorScheme.onPrimary,
    unselectedContentColor: Color = DialogTheme.colorScheme.onSurfaceVariant,
) {
    val backgroundColor: Color by animateColorAsState(
        targetValue = if (selected) selectedBackgroundColor else unselectedBackgroundColor,
        animationSpec = tween(durationMillis = 300),
    )

    val contentColor: Color by animateColorAsState(
        targetValue = if (selected) selectedContentColor else unselectedContentColor,
        animationSpec = tween(durationMillis = 300),
    )

    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            .clip(DialogTheme.shapes.small)
            .background(backgroundColor),
        text = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun DialogButtonPreviewLight() {
    DialogTheme {
        AnimatedTabPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogButtonPreviewDark() {
    DialogTheme(darkTheme = true) {
        AnimatedTabPreviewContent()
    }
}

@Composable
private fun AnimatedTabPreviewContent() {
    SecondaryTabRow(
        selectedTabIndex = 0,
        indicator = {},
        divider = {},
    ) {
        AnimatedTab(
            title = "작성",
            selected = true,
            onClick = {},
        )
        AnimatedTab(
            title = "미리보기",
            selected = false,
            onClick = {},
        )
    }
}
