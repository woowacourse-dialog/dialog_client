package com.on.dialog.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.core.common.Platform
import com.on.dialog.core.common.currentPlatform
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.main.DialogAppState
import dialog.feature.main.generated.resources.Res
import dialog.feature.main.generated.resources.top_level_back_press_exit_guide
import org.jetbrains.compose.resources.getString

@Composable
internal fun ExitConfirmationHandler(
    appState: DialogAppState,
) {
    var canExit: Boolean by rememberSaveable { mutableStateOf(false) }
    val isTopLevelRoot: Boolean = appState.navigationState.isTopLevelKey

    LaunchedEffect(canExit, isTopLevelRoot) {
        if (!canExit) return@LaunchedEffect

        if (!isTopLevelRoot) {
            canExit = false
            appState.snackbarDelegate.dismissCurrentSnackbar()
            return@LaunchedEffect
        }

        appState.snackbarDelegate.showSnackbar(
            state = SnackbarState.DEFAULT,
            message = getString(Res.string.top_level_back_press_exit_guide),
            onDismiss = { canExit = false },
        )
    }

    LaunchedEffect(appState.currentScreenKey) {
        appState.snackbarDelegate.dismissCurrentSnackbar()
    }

    NavigationBackHandler(
        state = rememberNavigationEventState(currentInfo = NavigationEventInfo.None),
        isBackEnabled = currentPlatform == Platform.Android && isTopLevelRoot && !canExit,
        onBackCompleted = { canExit = true },
    )
}
