package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState

@Immutable
internal data class CreateDiscussionState(
    val isSubmitting: Boolean = false,
) : UiState
