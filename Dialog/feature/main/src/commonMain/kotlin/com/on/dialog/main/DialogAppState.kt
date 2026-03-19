package com.on.dialog.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.NavKey
import com.on.dialog.designsystem.component.snackbar.SnackbarDelegate
import com.on.dialog.navigation.NavigationState
import kotlinx.coroutines.CoroutineScope

@Stable
class DialogAppState(
    val snackbarDelegate: SnackbarDelegate,
    val navigationState: NavigationState,
) {
    val snackbarHostState: SnackbarHostState = snackbarDelegate.snackbarHostState

    val currentScreenKey: NavKey
        get() = navigationState.currentKey

    val shouldShowBottomBar: Boolean
        get() = navigationState.currentKey in TopLevel.routes.keys
}

@Composable
fun rememberDialogAppState(
    navigationState: NavigationState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): DialogAppState = remember(snackbarHostState, coroutineScope, navigationState) {
    DialogAppState(
        snackbarDelegate = SnackbarDelegate(snackbarHostState, coroutineScope),
        navigationState = navigationState,
    )
}
