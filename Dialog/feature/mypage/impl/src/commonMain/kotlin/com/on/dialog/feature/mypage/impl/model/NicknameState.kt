package com.on.dialog.feature.mypage.impl.model

enum class NicknameState(
    val message: String,
) {
    Blank("닉네임을 입력해 주세요"),
    TooLong("닉네임이 너무 길어요"),
    Valid(""),
    ;

    companion object {
        fun of(nickname: String): NicknameState {
            if (nickname.isBlank()) return Blank
            if (nickname.length > 12) return TooLong
            return Valid
        }
    }
}
