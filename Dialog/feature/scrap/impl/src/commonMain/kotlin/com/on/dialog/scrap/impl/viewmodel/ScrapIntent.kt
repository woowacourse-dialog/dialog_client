package com.on.dialog.scrap.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent

internal sealed interface ScrapIntent : UiIntent {
    data object LoadNextPage : ScrapIntent

    data object Refresh : ScrapIntent
}
