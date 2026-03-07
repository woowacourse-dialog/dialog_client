package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.ui.viewmodel.BaseViewModel

internal class CreateDiscussionViewModel :
    BaseViewModel<CreateDiscussionIntent, CreateDiscussionState, CreateDiscussionEffect>(
        initialState = CreateDiscussionState(),
    ) {
    override fun onIntent(intent: CreateDiscussionIntent) {
        when (intent) {
            is CreateDiscussionIntent.OnContentChange -> TODO()
            is CreateDiscussionIntent.OnDiscussionTypeChange -> TODO()
            is CreateDiscussionIntent.OnEndDateChange -> TODO()
            CreateDiscussionIntent.OnSubmitClick -> TODO()
            is CreateDiscussionIntent.OnTitleChange -> TODO()
            is CreateDiscussionIntent.OnTrackChange -> TODO()
        }
    }
}