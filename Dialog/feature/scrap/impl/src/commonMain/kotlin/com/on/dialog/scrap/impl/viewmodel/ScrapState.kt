package com.on.dialog.scrap.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.scrap.impl.model.ScrapUiModel
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal sealed interface ScrapState : UiState {
    /**
     * 현재까지 화면에 쌓여있는 토론 목록(없으면 empty).
     *
     * - Loading: 기존 목록이 있을 수 있음(페이징/리프레시 등)
     * - Empty: 항상 empty
     * - Content: 항상 non-empty를 의도
     */
    val scraps: ImmutableList<ScrapUiModel>

    /**
     * 새로 받아온 토론 목록을 반영한 다음 상태를 반환한다.
     *
     * - 기존 데이터가 있고 새 데이터가 비어있어도 Content 유지
     * - 중복(id 기준)은 제거
     */
    fun update(newDiscussions: ImmutableList<ScrapUiModel>): ScrapState {
        val merged: ImmutableList<ScrapUiModel> = mergeUnique(
            current = scraps,
            incoming = newDiscussions,
        )

        if (merged.isEmpty()) return Empty
        return Content(scraps = merged)
    }

    @Immutable
    data object UnAuthorized : ScrapState {
        override val scraps: ImmutableList<ScrapUiModel> = persistentListOf()
    }

    @Immutable
    data class Loading(
        override val scraps: ImmutableList<ScrapUiModel> = persistentListOf(),
    ) : ScrapState

    @Immutable
    data object Empty : ScrapState {
        override val scraps: ImmutableList<ScrapUiModel> = persistentListOf()
    }

    @Immutable
    data class Content(
        override val scraps: ImmutableList<ScrapUiModel>,
    ) : ScrapState

    companion object {
        private fun mergeUnique(
            current: ImmutableList<ScrapUiModel>,
            incoming: ImmutableList<ScrapUiModel>,
        ): ImmutableList<ScrapUiModel> =
            (current + incoming)
                .distinctBy { discussion -> discussion.id }
                .toImmutableList()
    }
}
