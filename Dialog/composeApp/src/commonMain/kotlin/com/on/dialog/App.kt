package com.on.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.on.dialog.designsystem.component.DialogNavigationBar
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.main.api.MainNavKey
import com.on.dialog.navigation.SavedStateConfigurationProvider
import com.on.dialog.navigation.appScreens
import com.on.navigation.NavigationState
import com.on.navigation.Navigator
import com.on.navigation.rememberNavigationState
import com.on.navigation.toEntries
import kotlinx.collections.immutable.toPersistentList
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val savedStateConfigurationProvider: SavedStateConfigurationProvider = koinInject()
    val navigationState: NavigationState = rememberNavigationState(
        startKey = MainNavKey,
        topLevelKeys = TopLevel.routesKey,
        configuration = savedStateConfigurationProvider.savedStateConfiguration,
    )
    val navigator = remember { Navigator(navigationState) }
    val entryProvider: (NavKey) -> NavEntry<NavKey> =
        entryProvider { appScreens(navigator, savedStateConfigurationProvider.providers) }

    DialogTheme {
        Scaffold(bottomBar = {
            if (navigationState.currentKey in TopLevel.routesKey) {
                DialogNavigationBar(
                    items = TopLevel.routesNavigationItem.toPersistentList(),
                    selectedIndex = TopLevel.routesKey.indexOf(navigationState.currentKey),
                    onSelectedIndexChange = { selectedIndex ->
                        navigator.navigate(TopLevel.routesKey.elementAt(selectedIndex))
                    },
                )
            }
        }) { paddingValues ->
            NavDisplay(
                entries = navigationState.toEntries { key ->
                    entryProvider(key)
                },
                onBack = { navigator.goBack() },
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
