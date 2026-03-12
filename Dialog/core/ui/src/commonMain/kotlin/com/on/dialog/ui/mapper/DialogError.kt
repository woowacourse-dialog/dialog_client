package com.on.dialog.ui.mapper

import com.on.dialog.model.error.DialogError
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.dialog_error_already_liked
import dialog.core.ui.generated.resources.dialog_error_already_participation_discussion
import dialog.core.ui.generated.resources.dialog_error_already_scrapped
import dialog.core.ui.generated.resources.dialog_error_bad_request
import dialog.core.ui.generated.resources.dialog_error_common
import dialog.core.ui.generated.resources.dialog_error_create_discussion_failed
import dialog.core.ui.generated.resources.dialog_error_discussion_already_started
import dialog.core.ui.generated.resources.dialog_error_exist_user_email
import dialog.core.ui.generated.resources.dialog_error_invalid_signup
import dialog.core.ui.generated.resources.dialog_error_login_required
import dialog.core.ui.generated.resources.dialog_error_not_found_discussion
import dialog.core.ui.generated.resources.dialog_error_not_found_user
import dialog.core.ui.generated.resources.dialog_error_not_liked_yet
import dialog.core.ui.generated.resources.dialog_error_not_registered_user
import dialog.core.ui.generated.resources.dialog_error_not_scrapped_yet
import dialog.core.ui.generated.resources.dialog_error_participation_limit_exceeded
import dialog.core.ui.generated.resources.dialog_error_registered_user
import dialog.core.ui.generated.resources.dialog_error_user_not_found
import org.jetbrains.compose.resources.StringResource

fun DialogError.toStringResource(): StringResource = when (this) {
    DialogError.INVALID_SIGNUP -> Res.string.dialog_error_invalid_signup
    DialogError.LOGIN_REQUIRED -> Res.string.dialog_error_login_required
    DialogError.BAD_REQUEST -> Res.string.dialog_error_bad_request
    DialogError.ALREADY_SCRAPPED -> Res.string.dialog_error_already_scrapped
    DialogError.NOT_SCRAPPED_YET -> Res.string.dialog_error_not_scrapped_yet
    DialogError.ALREADY_LIKED -> Res.string.dialog_error_already_liked
    DialogError.NOT_LIKED_YET -> Res.string.dialog_error_not_liked_yet
    DialogError.NOT_FOUND_USER -> Res.string.dialog_error_not_found_user
    DialogError.NOT_FOUND_DISCUSSION -> Res.string.dialog_error_not_found_discussion
    DialogError.ALREADY_PARTICIPATION_DISCUSSION -> Res.string.dialog_error_already_participation_discussion
    DialogError.PARTICIPATION_LIMIT_EXCEEDED -> Res.string.dialog_error_participation_limit_exceeded
    DialogError.CREATE_DISCUSSION_FAILED -> Res.string.dialog_error_create_discussion_failed
    DialogError.DISCUSSION_ALREADY_STARTED -> Res.string.dialog_error_discussion_already_started
    DialogError.USER_NOT_FOUND -> Res.string.dialog_error_user_not_found
    DialogError.EXIST_USER_EMAIL -> Res.string.dialog_error_exist_user_email
    DialogError.REGISTERED_USER -> Res.string.dialog_error_registered_user
    DialogError.NOT_REGISTERED_USER -> Res.string.dialog_error_not_registered_user
}

val UNKNOWN_DIALOG_ERROR = Res.string.dialog_error_common

fun String?.toDialogErrorStringResource(): StringResource = this
    ?.let(DialogError::fromCode)
    ?.toStringResource()
    ?: Res.string.dialog_error_common
