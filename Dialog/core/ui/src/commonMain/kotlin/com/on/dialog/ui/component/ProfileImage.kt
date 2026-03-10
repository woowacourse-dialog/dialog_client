package com.on.dialog.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.no_profile

@Composable
fun ProfileImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    crossfade: Boolean = true,
    onSuccess: () -> Unit = {},
    onLoading: () -> Unit = {},
    onError: () -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    DialogAsyncImage(
        imageUrl = imageUrl,
        contentDescription = "Profile Image",
        placeholder = Res.drawable.no_profile,
        fallback = Res.drawable.no_profile,
        error = Res.drawable.no_profile,
        crossfade = crossfade,
        onSuccess = onSuccess,
        onLoading = onLoading,
        onError = onError,
        modifier = modifier
            .clip(CircleShape)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
    )
}

@Preview
@Composable
private fun ProfileImagePreview() {
    DialogTheme {
        ProfileImage(
            imageUrl = "",
            modifier = Modifier.size(32.dp),
        )
    }
}
