package com.on.dialog.feature.login.impl.model

import dialog.feature.login.impl.generated.resources.Res
import dialog.feature.login.impl.generated.resources.error_invalid_url
import dialog.feature.login.impl.generated.resources.error_jsession_not_found
import org.jetbrains.compose.resources.StringResource

enum class LoginError(
    val message: StringResource,
) {
    JSESSION_NOT_FOUND(message = Res.string.error_jsession_not_found),
    INVALID_URL(message = Res.string.error_invalid_url),
}
