package com.on.dialog.feature.discussionlist.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel
import com.on.dialog.feature.discussionlist.impl.model.SelectedFilters
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class DiscussionListState(
    val discussions: ImmutableList<DiscussionUiModel> = persistentListOf(),
    val filter: SelectedFilters = SelectedFilters(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
) : UiState {
    val filteredDiscussions: ImmutableList<DiscussionUiModel> =
        discussions
            .filter { discussion ->
                if (filter.noFiltersSelected) return@filter true
                filter.matches(discussion)
            }.toImmutableList()

    val shouldShowEmptyView: Boolean = filteredDiscussions.isEmpty()
}
