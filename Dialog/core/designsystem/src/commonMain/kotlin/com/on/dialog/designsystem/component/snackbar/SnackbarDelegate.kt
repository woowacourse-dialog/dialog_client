package com.on.dialog.designsystem.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

val LocalSnackbarDelegate = compositionLocalOf<SnackbarDelegate> {
    error("No SnackbarDelegate provided")
}

@Stable
class SnackbarDelegate(
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
) {
    private var snackbarJob: Job? = null
    private var isShowingNonDismissable: Boolean = false

    fun showSnackbar(
        state: SnackbarState,
        message: String,
        nonDismissable: Boolean = false,
        actionLabel: String? = null,
        withDismissAction: Boolean = actionLabel == null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onDismiss: () -> Unit = {},
        onAction: () -> Unit = {},
    ) {
        snackbarJob?.cancel()
        val currentJob: Job =
            coroutineScope.launch {
                val visuals = DialogSnackbarVisuals(
                    message = message,
                    actionLabel = actionLabel,
                    withDismissAction = withDismissAction,
                    duration = duration,
                    state = state,
                )
                try {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(visuals)
                    when (result) {
                        SnackbarResult.Dismissed -> onDismiss()
                        SnackbarResult.ActionPerformed -> onAction()
                    }
                } catch (e: CancellationException) {
                    onDismiss()
                    throw e
                }
            }
        snackbarJob = currentJob
        isShowingNonDismissable = nonDismissable
        currentJob.invokeOnCompletion {
            if (snackbarJob === currentJob) {
                isShowingNonDismissable = false
                snackbarJob = null
            }
        }
    }

    fun dismissCurrentSnackbar() {
        if (isShowingNonDismissable) return
        snackbarJob?.cancel()
        snackbarJob = null
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}
