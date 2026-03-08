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
    val mode: DiscussionMode = DiscussionMode.Online(),
    val isSubmitting: Boolean = false,
) : UiState {
    val isSubmitEnabled: Boolean
        get() =
            when {
                title.isBlank() -> false
                selectedTrackIndex !in trackOptions.indices -> false
                else -> mode.isValid
            }
}

@Immutable
internal sealed interface DiscussionMode {
    val isValid: Boolean

    @Immutable
    data class Online(
        val endDateOptions: List<String> = listOf("1일 후", "2일 후", "3일 후"),
        val selectedEndDateIndex: Int = -1,
    ) : DiscussionMode {
        override val isValid: Boolean
            get() = selectedEndDateIndex in endDateOptions.indices
    }

    @Immutable
    data class Offline(
        val place: String = "",
        val participantCount: Int = 2,
        val selectedDate: LocalDate? = null,
        val selectedStartTime: LocalTime? = null,
        val selectedEndTime: LocalTime? = null,
    ) : DiscussionMode {
        override val isValid: Boolean
            get() = place.isNotBlank() &&
                selectedDate != null &&
                selectedStartTime != null &&
                selectedEndTime != null
    }
}
