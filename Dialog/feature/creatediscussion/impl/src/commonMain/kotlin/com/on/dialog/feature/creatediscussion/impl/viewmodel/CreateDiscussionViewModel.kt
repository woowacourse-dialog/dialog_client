package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.ui.viewmodel.BaseViewModel

internal class CreateDiscussionViewModel :
    BaseViewModel<CreateDiscussionIntent, CreateDiscussionState, CreateDiscussionEffect>(
        initialState = CreateDiscussionState(),
    ) {
    override fun onIntent(intent: CreateDiscussionIntent) {

    }
}
