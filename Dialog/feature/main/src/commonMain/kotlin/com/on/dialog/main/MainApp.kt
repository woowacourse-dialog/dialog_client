package com.on.dialog.main

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.NavigationEventState
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.core.common.Platform
import com.on.dialog.core.common.currentPlatform
import com.on.dialog.designsystem.component.snackbar.DialogSnackbar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.discussionlist.api.DiscussionListNavKey
import com.on.dialog.main.component.DialogNavigationBar
import com.on.dialog.main.navigation.SavedStateConfigurationProvider
import com.on.dialog.main.navigation.appScreens
import com.on.dialog.navigation.Navigator
import com.on.dialog.navigation.rememberNavigationState
import com.on.dialog.navigation.toEntries
import dialog.feature.main.generated.resources.Res
import dialog.feature.main.generated.resources.top_level_back_press_exit_guide
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun MainApp(savedStateConfigurationProvider: SavedStateConfigurationProvider = koinInject()) {
    val navigationState = rememberNavigationState(
        startKey = DiscussionListNavKey,
        topLevelKeys = TopLevel.routes.keys,
        configuration = savedStateConfigurationProvider.savedStateConfiguration,
    )

    val appState: DialogAppState = rememberDialogAppState(navigationState = navigationState)
    val navigator: Navigator = remember { Navigator(appState.navigationState) }
    var canExit: Boolean by rememberSaveable { mutableStateOf(false) }
    val isTopLevelRoot: Boolean = appState.navigationState.isTopLevelKey
    val exitMessage: String = stringResource(resource = Res.string.top_level_back_press_exit_guide)
    val navState: NavigationEventState<NavigationEventInfo.None> =
        rememberNavigationEventState(currentInfo = NavigationEventInfo.None)

    LaunchedEffect(canExit, isTopLevelRoot) {
        if (!canExit || !isTopLevelRoot) {
            return@LaunchedEffect
        }
        delay(timeMillis = 2000)
        canExit = false
    }

    NavigationBackHandler(
        state = navState,
        isBackEnabled = currentPlatform == Platform.Android && isTopLevelRoot && !canExit,
        onBackCompleted = {
            canExit = true
            appState.snackbarDelegate.showSnackbar(
                state = SnackbarState.DEFAULT,
                message = exitMessage,
            )
        },
    )

    DialogTheme {
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
                    snackbar = { snackbarData ->
                        DialogSnackbar(snackbarData = snackbarData)
                    },
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
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
                    onBack = {
                        navigator.goBack()
                    },
                    modifier = Modifier
                        .padding(paddingValues)
                        .consumeWindowInsets(paddingValues)
                        .background(DialogTheme.colorScheme.surfaceContainer),
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
}
