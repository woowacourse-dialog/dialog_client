package com.on.dialog.feature.discussionlist.impl.model

import androidx.compose.runtime.Immutable
import com.on.dialog.model.discussion.criteria.DiscussionCriteria

@Immutable
internal data class SelectedFilters(
    val selectedTrackFilter: List<TrackUiModel> = emptyList(),
    val selectedTypeFilter: List<DiscussionTypeUiModel> = emptyList(),
    val selectedStatusFilter: List<DiscussionStatusUiModel> = emptyList(),
) {
    val noFiltersSelected: Boolean = selectedTrackFilter.isEmpty() &&
        selectedTypeFilter.isEmpty() &&
        selectedStatusFilter.isEmpty()

    fun updateTrackFilter(track: TrackUiModel): SelectedFilters = copy(
        selectedTrackFilter = if (selectedTrackFilter.contains(track)) {
            selectedTrackFilter - track
        } else {
            selectedTrackFilter + track
        },
    )

    fun updateDiscussionTypeFilter(discussionType: DiscussionTypeUiModel): SelectedFilters = copy(
        selectedTypeFilter = if (selectedTypeFilter.contains(discussionType)) {
            selectedTypeFilter - discussionType
        } else {
            selectedTypeFilter + discussionType
        },
    )

    fun updateDiscussionStatusFilter(discussionStatus: DiscussionStatusUiModel): SelectedFilters =
        copy(
            selectedStatusFilter =
                if (selectedStatusFilter.contains(discussionStatus)) {
                    selectedStatusFilter - discussionStatus
                } else {
                    selectedStatusFilter + discussionStatus
                },
        )

    fun matches(discussion: DiscussionUiModel): Boolean {
        val trackOk =
            if (selectedTrackFilter.isEmpty()) true else selectedTrackFilter.contains(discussion.track)

        val typeOk =
            if (selectedTypeFilter.isEmpty()) true else selectedTypeFilter.contains(discussion.type)

        val statusOk =
            if (selectedStatusFilter.isEmpty()) true else selectedStatusFilter.contains(discussion.status)

        return trackOk && typeOk && statusOk
    }

    fun toDomain(): DiscussionCriteria =
        DiscussionCriteria(
            tracks = selectedTrackFilter.map(TrackUiModel::toDomain),
            statuses = selectedStatusFilter.map(DiscussionStatusUiModel::toDomain),
            discussionTypes = selectedTypeFilter.map(DiscussionTypeUiModel::toDomain),
        )
}
