package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Immutable
internal data class CreateDiscussionState(
    val title: String = "",
    val trackOptions: List<String> = listOf("안드로이드", "백엔드", "프론트엔드"),
    val selectedTrackIndex: Int = -1,
    val isMeetupEnabled: Boolean = false,
    val place: String = "",
    val participantCount: Int = 0,
    val selectedDate: LocalDate? = null,
    val selectedStartTime: LocalTime? = null,
    val selectedEndTime: LocalTime? = null,
    val endDateOptions: List<String> = listOf("1일 후", "2일 후", "3일 후"),
    val selectedEndDateIndex: Int = -1,
    val isSubmitting: Boolean = false,
) : UiState {
    val isSubmitEnabled: Boolean
        get() {
            if (title.isBlank()) return false
            if (selectedTrackIndex !in trackOptions.indices) return false

            if (isMeetupEnabled) {
                return place.isNotBlank() &&
                    selectedDate != null &&
                    selectedStartTime != null &&
                    selectedEndTime != null
            }

            return selectedEndDateIndex in endDateOptions.indices
        }
}
