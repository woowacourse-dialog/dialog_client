package com.on.dialog.scrap.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.scrap.impl.model.ScrapUiModel
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal sealed interface ScrapState : UiState {
    val scraps: ImmutableList<ScrapUiModel>

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
}
