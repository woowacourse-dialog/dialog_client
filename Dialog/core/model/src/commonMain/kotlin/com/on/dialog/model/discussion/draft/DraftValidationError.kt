package com.on.dialog.model.discussion.draft

sealed interface DraftValidationError {
    sealed interface Online : DraftValidationError {
        data object EndDateNotAfterToday : Online
    }

    sealed interface Offline : DraftValidationError {
        data object StartDateNotAfterToday : Offline
        data object EndDateNotAfterToday : Offline
        data object StartNotBeforeEnd : Offline
        data object StartTimeOutOfRange : Offline
        data object EndTimeOutOfRange : Offline
        data object ParticipantCountTooLow : Offline
    }
}