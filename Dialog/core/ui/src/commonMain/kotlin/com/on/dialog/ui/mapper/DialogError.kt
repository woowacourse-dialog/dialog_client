package com.on.dialog.ui.mapper

import com.on.dialog.model.error.DialogError
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.dialog_error_already_discussion_summary
import dialog.core.ui.generated.resources.dialog_error_already_liked
import dialog.core.ui.generated.resources.dialog_error_already_participation_discussion
import dialog.core.ui.generated.resources.dialog_error_already_reported
import dialog.core.ui.generated.resources.dialog_error_already_scrapped
import dialog.core.ui.generated.resources.dialog_error_bad_request
import dialog.core.ui.generated.resources.dialog_error_cannot_delete_discussion
import dialog.core.ui.generated.resources.dialog_error_cannot_report_own_content
import dialog.core.ui.generated.resources.dialog_error_cannot_summarize_offline_discussion
import dialog.core.ui.generated.resources.dialog_error_comment_not_found
import dialog.core.ui.generated.resources.dialog_error_common
import dialog.core.ui.generated.resources.dialog_error_conflict_profile_image
import dialog.core.ui.generated.resources.dialog_error_create_discussion_failed
import dialog.core.ui.generated.resources.dialog_error_discussion_already_started
import dialog.core.ui.generated.resources.dialog_error_exist_user_email
import dialog.core.ui.generated.resources.dialog_error_failed_save_image
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_content
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_max_participants
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_start_time
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_summary
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_time
import dialog.core.ui.generated.resources.dialog_error_invalid_discussion_title
import dialog.core.ui.generated.resources.dialog_error_invalid_image_format
import dialog.core.ui.generated.resources.dialog_error_invalid_nickname_length
import dialog.core.ui.generated.resources.dialog_error_invalid_online_discussion_end_date
import dialog.core.ui.generated.resources.dialog_error_invalid_signup
import dialog.core.ui.generated.resources.dialog_error_login_required
import dialog.core.ui.generated.resources.dialog_error_not_found_discussion
import dialog.core.ui.generated.resources.dialog_error_not_liked_yet
import dialog.core.ui.generated.resources.dialog_error_not_offline_discussion
import dialog.core.ui.generated.resources.dialog_error_not_online_discussion
import dialog.core.ui.generated.resources.dialog_error_not_registered_user
import dialog.core.ui.generated.resources.dialog_error_not_scrapped_yet
import dialog.core.ui.generated.resources.dialog_error_participation_limit_exceeded
import dialog.core.ui.generated.resources.dialog_error_profile_image_not_found
import dialog.core.ui.generated.resources.dialog_error_registered_user
import dialog.core.ui.generated.resources.dialog_error_reply_depth_exceeded
import dialog.core.ui.generated.resources.dialog_error_unauthorized_discussion_summary
import dialog.core.ui.generated.resources.dialog_error_user_not_found
import dialog.core.ui.generated.resources.dialog_error_withdraw_user
import org.jetbrains.compose.resources.StringResource

fun DialogError.toStringResource(): StringResource = when (this) {
    DialogError.INVALID_SIGNUP -> Res.string.dialog_error_invalid_signup

    DialogError.LOGIN_REQUIRED -> Res.string.dialog_error_login_required

    DialogError.WITHDRAW_USER -> Res.string.dialog_error_withdraw_user

    DialogError.BAD_REQUEST -> Res.string.dialog_error_bad_request

    DialogError.ALREADY_SCRAPPED -> Res.string.dialog_error_already_scrapped

    DialogError.NOT_SCRAPPED_YET -> Res.string.dialog_error_not_scrapped_yet

    DialogError.ALREADY_LIKED -> Res.string.dialog_error_already_liked

    DialogError.NOT_LIKED_YET -> Res.string.dialog_error_not_liked_yet

    DialogError.NOT_FOUND_DISCUSSION -> Res.string.dialog_error_not_found_discussion

    DialogError.ALREADY_PARTICIPATION_DISCUSSION -> Res.string.dialog_error_already_participation_discussion

    DialogError.PARTICIPATION_LIMIT_EXCEEDED -> Res.string.dialog_error_participation_limit_exceeded

    DialogError.CREATE_DISCUSSION_FAILED -> Res.string.dialog_error_create_discussion_failed

    DialogError.DISCUSSION_ALREADY_STARTED -> Res.string.dialog_error_discussion_already_started

    DialogError.CANNOT_DELETE_DISCUSSION -> Res.string.dialog_error_cannot_delete_discussion

    DialogError.CANNOT_SUMMARIZE_OFFLINE_DISCUSSION -> Res.string.dialog_error_cannot_summarize_offline_discussion

    DialogError.UNAUTHORIZED_DISCUSSION_SUMMARY -> Res.string.dialog_error_unauthorized_discussion_summary

    DialogError.ALREADY_DISCUSSION_SUMMARY -> Res.string.dialog_error_already_discussion_summary

    DialogError.USER_NOT_FOUND -> Res.string.dialog_error_user_not_found

    DialogError.EXIST_USER_EMAIL -> Res.string.dialog_error_exist_user_email

    DialogError.REGISTERED_USER -> Res.string.dialog_error_registered_user

    DialogError.NOT_REGISTERED_USER -> Res.string.dialog_error_not_registered_user

    DialogError.INVALID_DISCUSSION_TITLE -> Res.string.dialog_error_invalid_discussion_title

    DialogError.INVALID_DISCUSSION_CONTENT -> Res.string.dialog_error_invalid_discussion_content

    DialogError.INVALID_DISCUSSION_SUMMARY -> Res.string.dialog_error_invalid_discussion_summary

    DialogError.INVALID_DISCUSSION_TIME -> Res.string.dialog_error_invalid_discussion_time

    DialogError.INVALID_DISCUSSION_START_TIME -> Res.string.dialog_error_invalid_discussion_start_time

    DialogError.INVALID_DISCUSSION_MAX_PARTICIPANTS -> Res.string.dialog_error_invalid_discussion_max_participants

    DialogError.INVALID_IMAGE_FORMAT -> Res.string.dialog_error_invalid_image_format

    DialogError.PROFILE_IMAGE_NOT_FOUND -> Res.string.dialog_error_profile_image_not_found

    DialogError.CONFLICT_PROFILE_IMAGE -> Res.string.dialog_error_conflict_profile_image

    DialogError.FAILED_SAVE_IMAGE -> Res.string.dialog_error_failed_save_image

    DialogError.COMMENT_NOT_FOUND -> Res.string.dialog_error_comment_not_found

    DialogError.REPLY_DEPTH_EXCEEDED -> Res.string.dialog_error_reply_depth_exceeded

    DialogError.NOT_OFFLINE_DISCUSSION -> Res.string.dialog_error_not_offline_discussion

    DialogError.NOT_ONLINE_DISCUSSION -> Res.string.dialog_error_not_online_discussion

    DialogError.INVALID_ONLINE_DISCUSSION_END_DATE -> Res.string.dialog_error_invalid_online_discussion_end_date

    DialogError.INVALID_NICKNAME_LENGTH -> Res.string.dialog_error_invalid_nickname_length

    DialogError.ALREADY_REPORTED -> Res.string.dialog_error_already_reported

    DialogError.CANNOT_REPORT_OWN_CONTENT -> Res.string.dialog_error_cannot_report_own_content

    DialogError.OAUTH_USER_ID_MISSING,
    DialogError.AUTHENTICATION_NOT_FOUND,
    DialogError.INVALID_USER_ID_FORMAT,
    DialogError.INVALID_IDENTITY_TOKEN,
    DialogError.APPLE_AUTH_SERVER_ERROR,
    DialogError.INVALID_SEARCH_TYPE,
    DialogError.PAGE_SIZE_TOO_LARGE,
    DialogError.MESSAGING_TOKEN_NOT_FOUND,
    DialogError.UNAUTHORIZED_TOKEN_ACCESS,
    DialogError.UNAUTHORIZED_ACCESS,
    DialogError.FAILED_LOAD_PROMPT,
    DialogError.FAILED_AI_SUMMARY,
    DialogError.NOTIFICATION_NOT_FOUND,
    -> Res.string.dialog_error_common
}

val UNKNOWN_DIALOG_ERROR = Res.string.dialog_error_common

fun String?.toDialogErrorStringResource(): StringResource = this
    ?.let(DialogError::fromCode)
    ?.toStringResource()
    ?: Res.string.dialog_error_common
