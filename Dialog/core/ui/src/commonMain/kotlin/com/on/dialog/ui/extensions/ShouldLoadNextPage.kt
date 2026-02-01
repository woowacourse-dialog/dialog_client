package com.on.dialog.ui.extensions

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.shouldLoadNextPage(
    threshold: Int = 5,
): Boolean {
    val layoutInfo = layoutInfo
    val totalItemsCount = layoutInfo.totalItemsCount
    if (totalItemsCount == 0) return false

    val lastVisibleItemIndex =
        layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return false

    return lastVisibleItemIndex + threshold >= totalItemsCount - 1
}
