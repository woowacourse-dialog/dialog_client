package com.on.dialog.feature.mycreated.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent

internal sealed interface MyCreatedIntent : UiIntent {
    data object LoadNextPage : MyCreatedIntent
}
