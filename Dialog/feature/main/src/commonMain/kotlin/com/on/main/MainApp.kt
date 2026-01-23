package com.on.main

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.on.main.component.DialogNavigationBar
import com.on.navigation.NavigationState
import com.on.navigation.Navigator
import com.on.navigation.rememberNavigationState
import com.on.navigation.toEntries

@Composable
@Preview
fun MainApp(
    startKey: NavKey,
    savedStateConfiguration: SavedStateConfiguration,
    registerScreens: EntryProviderScope<NavKey>.(Navigator) -> Unit,
) {
    val navigationState: NavigationState = rememberNavigationState(
        startKey = startKey,
        topLevelKeys = TopLevel.routes.keys,
        configuration = savedStateConfiguration,
    )
    val navigator = remember { Navigator(navigationState) }

    Scaffold(
        bottomBar = {
            MainBottomBar(
                currentKey = navigationState.currentKey,
                onNavigate = navigator::navigate,
            )
        },
    ) { paddingValues ->
        NavDisplay(
            entries = navigationState.toEntries { key ->
                entryProvider { registerScreens(navigator) }.invoke(key)
            },
            onBack = navigator::goBack,
            modifier = Modifier.padding(paddingValues),
            transitionSpec = {
                ContentTransform(
                    fadeIn(tween(300)),
                    fadeOut(tween(300)),
                )
            },
            popTransitionSpec = {
                ContentTransform(
                    fadeIn(tween(300)),
                    fadeOut(tween(300)),
                )
            },
        )
    }
}

@Composable
private fun MainBottomBar(
    currentKey: NavKey,
    onNavigate: (NavKey) -> Unit,
) {
    if (currentKey !in TopLevel.routes.keys) return

    DialogNavigationBar(
        items = TopLevel.routes,
        selectedKey = currentKey,
        onSelectedKeyChange = onNavigate,
    )
}
