package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.now
import com.on.dialog.feature.creatediscussion.impl.mapper.toEndDateOffsetDays
import com.on.dialog.feature.creatediscussion.impl.mapper.toFullNameRes
import com.on.dialog.feature.creatediscussion.impl.mapper.toTrackCategory
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.draft.DiscussionDraft
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import com.on.dialog.model.discussion.draft.OnlineDiscussionDraft
import com.on.dialog.ui.viewmodel.UiState
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_end_date_option_1
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_end_date_option_2
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_end_date_option_3
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.StringResource
import kotlin.time.ExperimentalTime

@Immutable
internal sealed interface CreateDiscussionState : UiState {
    val title: String
    val trackOptions: ImmutableList<StringResource>
    val selectedTrackIndex: Int
    val content: String
    val isSubmitting: Boolean
    val isModeValid: Boolean

    val isSubmitEnabled: Boolean
        get() =
            when {
                title.isBlank() -> false
                selectedTrackIndex !in trackOptions.indices -> false
                content.isBlank() -> false
                else -> isModeValid
            }

    @Immutable
    data class Online(
        override val title: String = "",
        override val trackOptions: ImmutableList<StringResource> = tracks,
        override val selectedTrackIndex: Int = -1,
        override val content: String = "",
        override val isSubmitting: Boolean = false,
        val endDateOptions: ImmutableList<StringResource> = deadlineChoices,
        val selectedEndDateIndex: Int = -1,
    ) : CreateDiscussionState {
        override val isModeValid: Boolean
            get() = selectedEndDateIndex in endDateOptions.indices

        companion object {
            private val deadlineChoices = persistentListOf(
                Res.string.create_discussion_end_date_option_1,
                Res.string.create_discussion_end_date_option_2,
                Res.string.create_discussion_end_date_option_3,
            )
        }
    }

    @Immutable
    data class Offline(
        override val title: String = "",
        override val trackOptions: ImmutableList<StringResource> = tracks,
        override val selectedTrackIndex: Int = -1,
        override val content: String = "",
        override val isSubmitting: Boolean = false,
        val place: String = "",
        val participantCount: Int = 2,
        val selectedDate: LocalDate? = null,
        val selectedDateErrorMessage: StringResource? = null,
        val selectedStartTime: LocalTime? = null,
        val selectedStartTimeErrorMessage: StringResource? = null,
        val selectedEndTime: LocalTime? = null,
        val selectedEndTimeErrorMessage: StringResource? = null,
    ) : CreateDiscussionState {
        override val isModeValid: Boolean
            get() = place.isNotBlank() &&
                    selectedDate != null &&
                    selectedStartTime != null &&
                    selectedEndTime != null
    }

    companion object {
        private val tracks = Track.entries
            .map { it.toFullNameRes() }
            .toPersistentList()
    }
}

enum class EndDateOption(val offsetDays: Int) {
    ONE_DAY_LATER(1),
    TWO_DAYS_LATER(2),
    THREE_DAYS_LATER(3),
}

private fun CreateDiscussionState.Offline.toDomain(): OfflineDiscussionDraft {
    val selectedDate = selectedDate ?: LocalDate(0, 0, 0)
    val selectedStartTime = selectedStartTime ?: LocalTime(0, 0)
    val selectedEndTime = selectedEndTime ?: LocalTime(0, 0)

    return OfflineDiscussionDraft(
        title = title.trim(),
        content = content.trim(),
        startAt = selectedDate.atTime(selectedStartTime.hour, selectedStartTime.minute),
        endAt = selectedDate.atTime(selectedEndTime.hour, selectedEndTime.minute),
        place = place.trim(),
        maxParticipantCount = participantCount.coerceIn(
            OfflineDiscussionDraft.MIN_PARTICIPANT_COUNT,
            OfflineDiscussionDraft.MAX_PARTICIPANT_COUNT,
        ),
        category = selectedTrackIndex.toTrackCategory(),
    )
}

@OptIn(ExperimentalTime::class)
private fun CreateDiscussionState.Online.toDomain(): OnlineDiscussionDraft {
    val today = LocalDateTime.now().date
    val endDate = today.plus(DatePeriod(days = selectedEndDateIndex.toEndDateOffsetDays()))
    return OnlineDiscussionDraft(
        title = title.trim(),
        content = content.trim(),
        endDate = endDate,
        category = selectedTrackIndex.toTrackCategory(),
    )
}

internal fun CreateDiscussionState.toDomain(): DiscussionDraft =
    when (this) {
        is CreateDiscussionState.Offline -> toDomain()
        is CreateDiscussionState.Online -> toDomain()
    }

internal fun CreateDiscussionState.update(
    title: String? = null,
    content: String? = null,
    selectedTrackIndex: Int? = null,
    isSubmitting: Boolean? = null,
): CreateDiscussionState = when (this) {
    is CreateDiscussionState.Online -> copy(
        title = title ?: this.title,
        content = content ?: this.content,
        selectedTrackIndex = selectedTrackIndex ?: this.selectedTrackIndex,
        isSubmitting = isSubmitting ?: this.isSubmitting,
    )

    is CreateDiscussionState.Offline -> copy(
        title = title ?: this.title,
        content = content ?: this.content,
        selectedTrackIndex = selectedTrackIndex ?: this.selectedTrackIndex,
        isSubmitting = isSubmitting ?: this.isSubmitting,
    )
}

internal fun CreateDiscussionState.toOnlineState(
    cached: CreateDiscussionState.Online,
): CreateDiscussionState.Online = cached.copy(
    title = title,
    content = content,
    selectedTrackIndex = selectedTrackIndex,
    isSubmitting = isSubmitting,
    trackOptions = trackOptions,
)

internal fun CreateDiscussionState.toOfflineState(
    cached: CreateDiscussionState.Offline,
): CreateDiscussionState.Offline = cached.copy(
    title = title,
    content = content,
    selectedTrackIndex = selectedTrackIndex,
    isSubmitting = isSubmitting,
    trackOptions = trackOptions,
)