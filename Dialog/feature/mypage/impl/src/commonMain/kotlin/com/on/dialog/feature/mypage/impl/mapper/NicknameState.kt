package com.on.dialog.feature.mypage.impl.mapper

import androidx.compose.runtime.Composable
import com.on.dialog.model.user.NicknameState
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.nickname_too_long
import dialog.feature.mypage.impl.generated.resources.nickname_too_short
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NicknameState.message(): String = when (this) {
    NicknameState.TooLong -> stringResource(Res.string.nickname_too_long)
    NicknameState.TooShort -> stringResource(Res.string.nickname_too_short)
    is NicknameState.Valid -> ""
}
