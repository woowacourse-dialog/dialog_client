package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.creatediscussion.impl.mapper.toEndDateOffsetDays
import com.on.dialog.feature.creatediscussion.impl.mapper.toFullNameRes
import com.on.dialog.feature.creatediscussion.impl.mapper.toTrackCategory
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import com.on.dialog.model.discussion.draft.OnlineDiscussionDraft
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Immutable
internal data class CreateDiscussionState(
    val title: String = "",
    val trackOptions: List<String> = tracks,
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

    companion object {
        private val tracks = Track.entries.filter { it != Track.COMMON }
            .map { it.toFullNameRes() }
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

internal fun CreateDiscussionState.toDomain(mode: DiscussionMode.Offline): OfflineDiscussionDraft =
    OfflineDiscussionDraft(
        title = title.trim(),
        content = title.trim(),
        startAt = mode.selectedDate!!.atTime(
            mode.selectedStartTime!!.hour,
            mode.selectedStartTime.minute,
        ),
        endAt = mode.selectedDate.atTime(mode.selectedEndTime!!.hour, mode.selectedEndTime.minute),
        place = mode.place.trim(),
        maxParticipantCount = mode.participantCount.coerceAtLeast(2),
        category = selectedTrackIndex.toTrackCategory(),
    )

@OptIn(ExperimentalTime::class)
internal fun CreateDiscussionState.toDomain(mode: DiscussionMode.Online): OnlineDiscussionDraft {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val endDate = today.plus(DatePeriod(days = mode.selectedEndDateIndex.toEndDateOffsetDays()))
    return OnlineDiscussionDraft(
        title = title.trim(),
        content = title.trim(),
        endDate = endDate,
        category = selectedTrackIndex.toTrackCategory(),
    )
}