package com.on.dialog.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.no_profile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    crossfade: Boolean = true,
    onSuccess: () -> Unit = {},
    onLoading: () -> Unit = {},
    onError: () -> Unit = {},
) {
    DialogAsyncImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        placeholder = Res.drawable.no_profile,
        fallback = Res.drawable.no_profile,
        error = Res.drawable.no_profile,
        crossfade = crossfade,
        onSuccess = onSuccess,
        onLoading = onLoading,
        onError = onError,
        modifier = modifier
            .border(width = 0.5.dp, color = DialogTheme.colorScheme.onSurface, shape = CircleShape)
            .clip(CircleShape),
    )
}

@Preview
@Composable
private fun ProfileImagePreview() {
    DialogTheme {
        ProfileImage(
            imageUrl = "",
            contentDescription = "",
            modifier = Modifier.size(32.dp),
        )
    }
}
