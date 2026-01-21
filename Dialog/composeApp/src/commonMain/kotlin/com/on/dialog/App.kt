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
import com.on.dialog.feature.main.api.MainRoute
import com.on.dialog.feature.mypage.api.MyPageRoute
import com.on.dialog.feature.scrap.api.ScrapRoute
import com.on.dialog.designsystem.component.NavigationItem
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.navigation.AppNavConfig
import com.on.navigation.NavigationState
import com.on.navigation.Navigator
import com.on.navigation.rememberNavigationState
import com.on.navigation.toEntries
import kotlinx.collections.immutable.toPersistentList
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val appNavConfig: AppNavConfig = koinInject()

    val navigationState: NavigationState = rememberNavigationState(
        startKey = MainRoute,
        topLevelKeys = TOP_LEVEL_ROUTES.keys,
        configuration = appNavConfig.savedStateConfiguration
    )

    val navigator = remember { Navigator(navigationState) }

    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
        appNavConfig.providers.forEach { provider ->
            with(provider) {
                registerScreens(navigator)
            }
        }
    }

    DialogTheme {
        Scaffold(bottomBar = {
            if (navigationState.currentKey in TOP_LEVEL_ROUTES.keys) {
                DialogNavigationBar(
                    items = TOP_LEVEL_ROUTES.values.toPersistentList(),
                    selectedIndex = TOP_LEVEL_ROUTES.keys.indexOf(navigationState.currentKey),
                    onSelectedIndexChange = { selectedIndex ->
                        navigator.navigate(TOP_LEVEL_ROUTES.keys.elementAt(selectedIndex))
                    },
                )
            }
        }) { paddingValues ->
            NavDisplay(
                entries = navigationState.toEntries{ key ->
                    entryProvider(key)
                },
                onBack = { navigator.goBack() },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
private val TOP_LEVEL_ROUTES = mapOf<NavKey, NavigationItem>(
    MainRoute to NavigationItem(icon = DialogIcons.Home, label = "홈"),
    ScrapRoute to NavigationItem(icon = DialogIcons.Bookmark, label = "북마크"),
    MyPageRoute to NavigationItem(icon = DialogIcons.Person, label = "마이페이지"),
)