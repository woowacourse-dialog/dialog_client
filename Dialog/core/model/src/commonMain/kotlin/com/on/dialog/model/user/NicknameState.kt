package com.on.dialog.model.user

sealed interface NicknameState {
    data object TooShort : NicknameState

    data object TooLong : NicknameState

    data class Valid(
        val nickname: String,
    ) : NicknameState

    companion object {
        fun from(nickname: String): NicknameState {
            if (nickname.length < MIN_LENGTH) return TooShort
            if (nickname.length > MAX_LENGTH) return TooLong
            return Valid(nickname)
        }

        private const val MIN_LENGTH = 2
        private const val MAX_LENGTH = 15
    }
}
