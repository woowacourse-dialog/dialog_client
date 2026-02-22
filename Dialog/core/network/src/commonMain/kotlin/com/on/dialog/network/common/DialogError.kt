package com.on.dialog.network.common

enum class HttpStatus {
    BAD_GATEWAY,
    UNAUTHORIZED,
    NOT_FOUND,
    BAD_REQUEST,
}

enum class DialogError(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    // 1XXX - 인증/보안 관련
    GITHUB_USER_ID_MISSING("1001", "GitHub에서 사용자 ID를 가져올 수 없습니다.", HttpStatus.BAD_GATEWAY),
    INVALID_SIGNUP("1002", "유효하지 않은 회원가입입니다.", HttpStatus.UNAUTHORIZED),
    LOGIN_REQUIRED("1005", "로그인 후 이용할 수 있습니다.", HttpStatus.UNAUTHORIZED),

    // 5XXX - 비즈니스 로직 관련
    BAD_REQUEST("5000", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),

    ALREADY_SCRAPPED("5001", "이미 스크랩을 했습니다.", HttpStatus.BAD_REQUEST),
    NOT_SCRAPPED_YET("5002", "스크랩이 되어 있지 않습니다.", HttpStatus.BAD_REQUEST),

    ALREADY_LIKED("5010", "이미 좋아요를 눌렀습니다.", HttpStatus.BAD_REQUEST),
    NOT_LIKED_YET("5011", "좋아요를 누르지 않았습니다.", HttpStatus.BAD_REQUEST),

    NOT_FOUND_USER("5021", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_DISCUSSION("5022", "토론을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_PARTICIPATION_DISCUSSION("5023", "이미 참여 중인 토론입니다.", HttpStatus.BAD_REQUEST),
    PARTICIPATION_LIMIT_EXCEEDED("5024", "최대 참여자 수를 초과했습니다.", HttpStatus.BAD_REQUEST),
    CREATE_DISCUSSION_FAILED("5025", "토론을 생성할 수 없습니다", HttpStatus.BAD_REQUEST),
    DISCUSSION_ALREADY_STARTED("5026", "이미 시작된 토론입니다.", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND("5031", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EXIST_USER_EMAIL("5032", "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    REGISTERED_USER("5033", "이미 회원가입한 회원입니다.", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_USER("5034", "회원가입하지 않은 회원입니다.", HttpStatus.BAD_REQUEST),
    ;

    companion object {
        fun fromCode(code: String): DialogError? = entries.find { it.code == code }
    }
}
