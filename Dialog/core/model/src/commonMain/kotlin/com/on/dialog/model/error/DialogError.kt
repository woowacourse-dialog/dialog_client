package com.on.dialog.model.error

enum class DialogErrorHttpStatus {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    CONFLICT,
    INTERNAL_SERVER_ERROR,
    BAD_GATEWAY,
}

enum class DialogError(
    val code: String,
    val message: String,
    val httpStatus: DialogErrorHttpStatus,
) {
    /**
     * 1XXX - 인증 / 보안
     */
    OAUTH_USER_ID_MISSING(
        "1001",
        "OAuth 제공자에서 사용자 ID를 가져올 수 없습니다.",
        DialogErrorHttpStatus.BAD_GATEWAY,
    ),
    INVALID_SIGNUP("1002", "유효하지 않은 회원가입입니다.", DialogErrorHttpStatus.UNAUTHORIZED),
    AUTHENTICATION_NOT_FOUND("1003", "인증 정보를 찾을 수 없습니다.", DialogErrorHttpStatus.UNAUTHORIZED),
    INVALID_USER_ID_FORMAT("1004", "유효하지 않은 인증 정보입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    LOGIN_REQUIRED("1005", "로그인 후 이용할 수 있습니다.", DialogErrorHttpStatus.UNAUTHORIZED),
    WITHDRAW_USER("1006", "탈퇴한 사용자입니다.", DialogErrorHttpStatus.FORBIDDEN),
    INVALID_IDENTITY_TOKEN("1007", "유효하지 않은 인증 토큰입니다.", DialogErrorHttpStatus.UNAUTHORIZED),
    APPLE_AUTH_SERVER_ERROR(
        "1008",
        "인증에 실패했습니다. 잠시 후 다시 시도해주세요.",
        DialogErrorHttpStatus.BAD_GATEWAY,
    ),

    /**
     * 5XXX - 공통 / 비즈니스
     */
    BAD_REQUEST("5000", "잘못된 요청입니다.", DialogErrorHttpStatus.BAD_REQUEST),

    /**
     * Scrap
     */
    ALREADY_SCRAPPED("5001", "이미 스크랩을 했습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    NOT_SCRAPPED_YET("5002", "스크랩이 되어 있지 않습니다.", DialogErrorHttpStatus.BAD_REQUEST),

    /**
     * Like
     */
    ALREADY_LIKED("5010", "이미 좋아요를 눌렀습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    NOT_LIKED_YET("5011", "좋아요를 누르지 않았습니다.", DialogErrorHttpStatus.BAD_REQUEST),

    /**
     * Discussion
     */
    NOT_FOUND_DISCUSSION("5022", "토론을 찾을 수 없습니다.", DialogErrorHttpStatus.NOT_FOUND),
    ALREADY_PARTICIPATION_DISCUSSION("5023", "이미 참여 중인 토론입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    PARTICIPATION_LIMIT_EXCEEDED("5024", "최대 참여자 수를 초과했습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    CREATE_DISCUSSION_FAILED("5025", "토론을 생성할 수 없습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    DISCUSSION_ALREADY_STARTED("5026", "이미 시작된 토론입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    CANNOT_DELETE_DISCUSSION("5027", "삭제할 수 없는 토론입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    INVALID_SEARCH_TYPE("5028", "유효하지 않은 검색 조건입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    PAGE_SIZE_TOO_LARGE("5029", "페이지의 크기는 50개가 최대입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    CANNOT_SUMMARIZE_OFFLINE_DISCUSSION(
        "5030",
        "오프라인 토론은 요약할 수 없습니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),

    /**
     * 주의:
     * 5031, 5032는 서버 문서상 중복 코드입니다.
     * fromCode() 사용 시 먼저 선언된 enum이 반환됩니다.
     * 서버 쪽 코드 정정이 필요합니다.
     */
    UNAUTHORIZED_DISCUSSION_SUMMARY(
        "5031",
        "토론 작성자만 요약을 생성할 수 있습니다",
        DialogErrorHttpStatus.FORBIDDEN,
    ),
    ALREADY_DISCUSSION_SUMMARY("5032", "토론 요약이 이미 존재합니다.", DialogErrorHttpStatus.BAD_REQUEST),

    /**
     * User
     */
    USER_NOT_FOUND("5031", "사용자를 찾을 수 없습니다.", DialogErrorHttpStatus.NOT_FOUND),
    EXIST_USER_EMAIL("5032", "이미 존재하는 이메일입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    REGISTERED_USER("5033", "이미 회원가입한 회원입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    NOT_REGISTERED_USER("5034", "회원가입하지 않은 회원입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    INVALID_DISCUSSION_TITLE(
        "5035",
        "제목은 1자 이상 50자 이하로 입력해야 합니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    INVALID_DISCUSSION_CONTENT(
        "5036",
        "내용은 1자 이상 10,000자 이하로 입력해야 합니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    INVALID_DISCUSSION_SUMMARY(
        "5037",
        "요약은 1자 이상 300자 이하로 입력해야 합니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    INVALID_DISCUSSION_TIME("5038", "토론 시작/종료 시간이 올바르지 않습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    INVALID_DISCUSSION_START_TIME(
        "5039",
        "토론 시작 시간은 08:00~23:00 사이여야 합니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    INVALID_DISCUSSION_MAX_PARTICIPANTS(
        "5040",
        "참여자 수는 1명 이상 10명 이하여야 합니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    MESSAGING_TOKEN_NOT_FOUND("5041", "메시징 토큰을 찾을 수 없습니다.", DialogErrorHttpStatus.NOT_FOUND),
    UNAUTHORIZED_TOKEN_ACCESS("5042", "토큰에 접근할 권한이 없습니다.", DialogErrorHttpStatus.FORBIDDEN),

    /**
     * Image / Profile
     */
    INVALID_IMAGE_FORMAT("5050", "지원하지 않는 이미지 형식입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    PROFILE_IMAGE_NOT_FOUND("5051", "프로필 이미지를 찾을 수 없습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    CONFLICT_PROFILE_IMAGE("5052", "프로필 이미지가 이미 존재합니다.", DialogErrorHttpStatus.CONFLICT),
    FAILED_SAVE_IMAGE("5053", "이미지 저장에 실패하였습니다.", DialogErrorHttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Comment
     */
    COMMENT_NOT_FOUND("5060", "댓글을 찾을 수 없습니다.", DialogErrorHttpStatus.NOT_FOUND),
    REPLY_DEPTH_EXCEEDED("5061", "답글에 답글을 달 수 없습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("5062", "접근 권한이 없습니다.", DialogErrorHttpStatus.FORBIDDEN),

    /**
     * Discussion Type / Validation
     */
    NOT_OFFLINE_DISCUSSION("5071", "오프라인 토론이 아닙니다.", DialogErrorHttpStatus.BAD_REQUEST),
    NOT_ONLINE_DISCUSSION("5072", "온라인 토론이 아닙니다.", DialogErrorHttpStatus.BAD_REQUEST),
    INVALID_ONLINE_DISCUSSION_END_DATE(
        "5073",
        "오늘로부터 최대 3일동안 토론을 열 수 있습니다.",
        DialogErrorHttpStatus.BAD_REQUEST,
    ),
    INVALID_NICKNAME_LENGTH("5074", "닉네임은 2글자 이상 15자 이내여야 합니다.", DialogErrorHttpStatus.BAD_REQUEST),

    /**
     * AI
     */
    FAILED_LOAD_PROMPT(
        "5080",
        "프롬프트 리소스 로드에 실패하였습니다.",
        DialogErrorHttpStatus.INTERNAL_SERVER_ERROR,
    ),
    FAILED_AI_SUMMARY("5081", "AI 요약에 실패하였습니다.", DialogErrorHttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Notification / Report
     */
    NOTIFICATION_NOT_FOUND("5090", "해당 알림을 찾을 수 없습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    ALREADY_REPORTED("5091", "이미 신고한 콘텐츠입니다.", DialogErrorHttpStatus.BAD_REQUEST),
    CANNOT_REPORT_OWN_CONTENT("5092", "본인의 콘텐츠는 신고할 수 없습니다.", DialogErrorHttpStatus.BAD_REQUEST),
    ;

    companion object {
        fun fromCode(code: String): DialogError? = entries.find { it.code == code }
    }
}
