package com.on.dialog.designsystem.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.util.dpToSp
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val DialogTypography
    @Composable
    get() =
        Typography(
            displayLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(57.dp),
                    lineHeight = dpToSp(64.dp),
                    letterSpacing = dpToSp((-0.25).dp),
                ),
            displayMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(45.dp),
                    lineHeight = dpToSp(52.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            displaySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(36.dp),
                    lineHeight = dpToSp(44.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(32.dp),
                    lineHeight = dpToSp(40.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(28.dp),
                    lineHeight = dpToSp(36.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(24.dp),
                    lineHeight = dpToSp(32.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            titleLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(22.dp),
                    lineHeight = dpToSp(28.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            titleMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(16.dp),
                    lineHeight = dpToSp(24.dp),
                    letterSpacing = dpToSp(0.15.dp),
                ),
            titleSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.1.dp),
                ),
            // Default Text Style
            bodyLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(16.dp),
                    lineHeight = dpToSp(24.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
            bodyMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.25.dp),
                ),
            bodySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(12.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.4.dp),
                ),
            // Used for Button
            labelLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.1.dp),
                ),
            // Used for Navigation Items
            labelMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(12.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
            // Used for Tag
            labelSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(11.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
        )

@Preview(name = "Light Typography", heightDp = 1200)
@Composable
private fun TypographyPreview() {
    DialogTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            TypographyContent()
        }
    }
}

@Preview(name = "Dark Typography", heightDp = 1200)
@Composable
private fun TypographyDarkPreview() {
    DialogTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            TypographyContent()
        }
    }
}

@Composable
private fun TypographyContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Typography Scale",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        TypographySection(title = "Display") {
            TypographyItem(
                label = "Display Large",
                style = MaterialTheme.typography.displayLarge,
            )
            TypographyItem(
                label = "Display Medium",
                style = MaterialTheme.typography.displayMedium,
            )
            TypographyItem(
                label = "Display Small",
                style = MaterialTheme.typography.displaySmall,
            )
        }

        TypographySection(title = "Headline") {
            TypographyItem(
                label = "Headline Large",
                style = MaterialTheme.typography.headlineLarge,
                sampleText = "Headline Large 32sp / 40sp / 0sp",
            )
            TypographyItem(
                label = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium,
                sampleText = "Headline Medium 28sp / 36sp / 0sp",
            )
            TypographyItem(
                label = "Headline Small",
                style = MaterialTheme.typography.headlineSmall,
                sampleText = "Headline Small 24sp / 32sp / 0sp",
            )
        }

        TypographySection(title = "Title") {
            TypographyItem(
                label = "Title Large",
                style = MaterialTheme.typography.titleLarge,
                sampleText = "Title Large 22sp / 28sp / 0sp",
            )
            TypographyItem(
                label = "Title Medium",
                style = MaterialTheme.typography.titleMedium,
                sampleText = "Title Medium 16sp / 24sp / 0.15sp",
            )
            TypographyItem(
                label = "Title Small",
                style = MaterialTheme.typography.titleSmall,
                sampleText = "Title Small 14sp / 20sp / 0.1sp",
            )
        }

        TypographySection(title = "Body") {
            TypographyItem(
                label = "Body Large",
                style = MaterialTheme.typography.bodyLarge,
                sampleText = "Body Large는 기본 본문 텍스트로 사용됩니다. 16sp / 24sp / 0.5sp",
            )
            TypographyItem(
                label = "Body Medium",
                style = MaterialTheme.typography.bodyMedium,
                sampleText = "Body Medium은 보조 본문 텍스트로 사용됩니다. 14sp / 20sp / 0.25sp",
            )
            TypographyItem(
                label = "Body Small",
                style = MaterialTheme.typography.bodySmall,
                sampleText = "Body Small은 작은 본문 텍스트로 사용됩니다. 12sp / 16sp / 0.4sp",
            )
        }

        TypographySection(title = "Label") {
            TypographyItem(
                label = "Label Large (Button)",
                style = MaterialTheme.typography.labelLarge,
                sampleText = "버튼 텍스트에 사용됩니다. 14sp / 20sp / 0.1sp",
            )
            TypographyItem(
                label = "Label Medium (Navigation)",
                style = MaterialTheme.typography.labelMedium,
                sampleText = "네비게이션 아이템에 사용됩니다. 12sp / 16sp / 0.5sp",
            )
            TypographyItem(
                label = "Label Small (Tag)",
                style = MaterialTheme.typography.labelSmall,
                sampleText = "태그에 사용됩니다. 11sp / 16sp / 0.5sp",
            )
        }
    }
}

@Composable
private fun TypographySection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        content()
    }
}

@Composable
private fun TypographyItem(
    label: String,
    style: TextStyle,
    sampleText: String = label,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = sampleText,
            style = style,
        )
    }
}
