package com.on.dialog.scrap.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
fun ScrapScreen(
    navigateToDiscussionDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = "스크랩 화면",
            centerAligned = true,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ScrapScreenPreview() {
    DialogTheme {
        ScrapScreen({})
    }
}
