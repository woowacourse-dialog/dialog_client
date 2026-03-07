package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState

@Immutable
internal data class CreateDiscussionState(
    val isSubmitting: Boolean = false,
    val title: String = "",
    val selectedTrack: String = "프론트엔드",
    val trackOptions: List<String> = listOf("프론트엔드", "백엔드", "안드로이드", "iOS", "데브옵스"),
    val isOnline: Boolean = true,
    val selectedEndDate: String = "1일 후",
    val endDateOptions: List<String> = listOf("1일 후", "3일 후", "7일 후", "14일 후", "30일 후"),
    val content: String = "",
) : UiState