package com.on.dialog.scrap.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect
import org.jetbrains.compose.resources.StringResource

internal sealed interface ScrapEffect : UiEffect {
    data class ShowSnackbar(
        val message: StringResource,
        val state: SnackbarState,
    ) : ScrapEffect
}
