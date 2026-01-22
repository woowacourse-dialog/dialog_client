package com.on.dialog.feature.main.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.component.DiscussionCard
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DiscussionListScreen(
    navigateToDiscussionDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DialogTheme.spacing.huge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "토론 목록",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.large))

        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
        ) {
            DiscussionCard(
                chips = persistentListOf( ChipCategory(
                    text = "Android",
                    textColor = Color(0xFF003D2E),
                    backgroundColor = Color(0xFF3DDC84),
                )),
                onChipsChange = {},
                title = "KMP 전망",
                author = "크림",
                endAt = "2026.01.31",
                discussionCount = 3,
            ){
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
