package com.on.dialog.designsystem.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val LocalSnackbarDelegate = compositionLocalOf<SnackbarDelegate> {
    error("No SnackbarDelegate provided")
}

@Stable
class SnackbarDelegate(
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
) {
    private var snackbarJob: Job? = null

    fun showSnackbar(
        state: SnackbarState,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = actionLabel == null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onDismiss: () -> Unit = {},
        onAction: () -> Unit = {},
    ) {
        snackbarJob?.cancel()
        snackbarJob = coroutineScope.launch {
            val visuals = DialogSnackbarVisuals(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration,
                state = state,
            )

            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(visuals)

            when (result) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onAction()
            }

            snackbarJob = null
        }
    }

    fun dismissCurrentSnackbar() {
        snackbarJob?.cancel()
        snackbarJob = null
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}
