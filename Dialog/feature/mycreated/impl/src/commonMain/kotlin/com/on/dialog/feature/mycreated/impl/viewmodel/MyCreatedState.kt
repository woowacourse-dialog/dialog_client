package com.on.dialog.feature.mycreated.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.mycreated.impl.model.DiscussionUiModel
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal sealed interface MyCreatedState : UiState {
    /**
     * 현재까지 화면에 쌓여있는 토론 목록(없으면 empty).
     *
     * - Loading: 기존 목록이 있을 수 있음(페이징/리프레시 등)
     * - Empty: 항상 empty
     * - Content: 항상 non-empty를 의도
     */
    val discussions: ImmutableList<DiscussionUiModel>

    /**
     * 새로 받아온 토론 목록을 반영한 다음 상태를 반환한다.
     *
     * - 기존 데이터가 있고 새 데이터가 비어있어도 Content 유지
     * - 중복(id 기준)은 제거
     */
    fun update(newDiscussions: ImmutableList<DiscussionUiModel>): MyCreatedState {
        val merged: ImmutableList<DiscussionUiModel> = mergeUnique(
            current = discussions,
            incoming = newDiscussions,
        )

        if (merged.isEmpty()) return Empty
        return Content(discussions = merged)
    }

    @Immutable
    data class Loading(
        override val discussions: ImmutableList<DiscussionUiModel> = persistentListOf(),
    ) : MyCreatedState

    @Immutable
    data object Empty : MyCreatedState {
        override val discussions: ImmutableList<DiscussionUiModel> = persistentListOf()
    }

    @Immutable
    data class Content(
        override val discussions: ImmutableList<DiscussionUiModel>,
    ) : MyCreatedState

    companion object {
        private fun mergeUnique(
            current: ImmutableList<DiscussionUiModel>,
            incoming: ImmutableList<DiscussionUiModel>,
        ): ImmutableList<DiscussionUiModel> =
            (current + incoming)
                .distinctBy { discussion -> discussion.id }
                .toImmutableList()
    }
}
