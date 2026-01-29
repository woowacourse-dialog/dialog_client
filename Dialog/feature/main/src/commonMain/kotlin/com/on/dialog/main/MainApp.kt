package com.on.dialog.main

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.on.dialog.designsystem.component.snackbar.DialogSnackbar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.main.component.DialogNavigationBar
import com.on.dialog.main.navigation.SavedStateConfigurationProvider
import com.on.dialog.main.navigation.appScreens
import com.on.dialog.navigation.Navigator
import com.on.dialog.navigation.rememberNavigationState
import com.on.dialog.navigation.toEntries
import org.koin.compose.koinInject

@Composable
@Preview
fun MainApp(savedStateConfigurationProvider: SavedStateConfigurationProvider = koinInject()) {
    val navigationState = rememberNavigationState(
        startKey = DiscussionListNavKey,
        topLevelKeys = TopLevel.routes.keys,
        configuration = savedStateConfigurationProvider.savedStateConfiguration,
    )

    val appState = rememberDialogAppState(navigationState = navigationState)
    val navigator = remember { Navigator(appState.navigationState) }

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                DialogNavigationBar(
                    items = TopLevel.routes,
                    selectedKey = appState.currentScreenKey,
                    onSelectedKeyChange = navigator::navigate,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                snackbar = ::DialogSnackbar,
            )
        },
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalSnackbarDelegate provides appState.snackbarDelegate,
        ) {
            NavDisplay(
                entries = appState.navigationState.toEntries { key ->
                    entryProvider {
                        appScreens(navigator, savedStateConfigurationProvider.providers)
                    }.invoke(key)
                },
                onBack = navigator::goBack,
                modifier = Modifier.padding(paddingValues),
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = fadeIn(animationSpec = tween(durationMillis = 300)),
                        initialContentExit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    )
                },
                popTransitionSpec = {
                    ContentTransform(
                        targetContentEnter = fadeIn(animationSpec = tween(durationMillis = 300)),
                        initialContentExit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    )
                },
            )
        }
    }
}
