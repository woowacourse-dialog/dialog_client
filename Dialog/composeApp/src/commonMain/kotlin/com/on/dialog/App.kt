package com.on.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.main.MainApp
import com.on.dialog.navigation.SavedStateConfigurationProvider
import com.on.dialog.navigation.appScreens
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val savedStateConfigurationProvider: SavedStateConfigurationProvider = koinInject()
    DialogTheme {
        MainApp(
            startKey = DiscussionListNavKey,
            savedStateConfiguration = savedStateConfigurationProvider.savedStateConfiguration,
            registerScreens = { navigator ->
                appScreens(navigator, savedStateConfigurationProvider.providers)
            },
        )
    }
}
