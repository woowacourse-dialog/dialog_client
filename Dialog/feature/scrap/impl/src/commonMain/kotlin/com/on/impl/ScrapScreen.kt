package com.on.impl

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
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
fun ScrapScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DialogTheme.spacing.huge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "내가 스크랩한 토론 목록",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.large))

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(DialogTheme.spacing.large),
                verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
            ) {
                Text(
                    text = "토론 주제 1",
                )
                Text(
                    text = "토론 주제 2",
                )
                Text(
                    text = "토론 주제 3",
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScrapScreenPreview() {
    DialogTheme {
        ScrapScreen()
    }
}
