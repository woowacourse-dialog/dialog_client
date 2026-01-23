package com.on.dialog.feature.discussionlist.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.component.DiscussionCard
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DiscussionListScreen(
    navigateToDiscussionDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = "토론 목록 화면",
            centerAligned = true,
        )

        Column(
            modifier = Modifier
                .padding(DialogTheme.spacing.huge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DiscussionCard(
                chips = persistentListOf(
                    ChipCategory(
                        text = "Android",
                        textColor = Color(0xFF003D2E),
                        backgroundColor = Color(0xFF3DDC84),
                    )
                ),
                onChipsChange = {},
                title = "KMP 전망",
                author = "크림",
                endAt = "2026.01.31",
                discussionCount = 3,
            ) {
                navigateToDiscussionDetail()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DiscussionListScreenPreview() {
    DialogTheme {
        DiscussionListScreen({})
    }
}
