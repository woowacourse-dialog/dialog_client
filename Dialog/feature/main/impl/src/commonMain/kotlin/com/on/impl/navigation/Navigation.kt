package com.on.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.on.api.MainNavigator
import com.on.api.MainRoute
import com.on.impl.DiscussionListScreen
import com.on.navigation.Navigator

class DefaultMainNavigator(
    private val navigator: Navigator
): MainNavigator{
    override fun navigateToMain() {
        navigator.navigate(MainRoute)
    }

}

fun EntryProviderScope<NavKey>.mainScreen(
    navigator: Navigator
) {
    entry<MainRoute> {
        DiscussionListScreen()
    }
}
