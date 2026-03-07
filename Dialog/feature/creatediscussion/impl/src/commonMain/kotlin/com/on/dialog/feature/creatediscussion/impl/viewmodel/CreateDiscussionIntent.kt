package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent

internal sealed interface CreateDiscussionIntent : UiIntent {
    data class OnTitleChange(val title: String) : CreateDiscussionIntent
    data class OnTrackChange(val track: String) : CreateDiscussionIntent
    data class OnDiscussionTypeChange(val isOnline: Boolean) : CreateDiscussionIntent
    data class OnEndDateChange(val endDate: String) : CreateDiscussionIntent
    data class OnContentChange(val content: String) : CreateDiscussionIntent
    data object OnSubmitClick : CreateDiscussionIntent
}