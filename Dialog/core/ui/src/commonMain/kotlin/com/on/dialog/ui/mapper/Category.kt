package com.on.dialog.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.on.dialog.ui.component.ChipCategory
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.category_android
import dialog.core.ui.generated.resources.category_backend
import dialog.core.ui.generated.resources.category_frontend
import dialog.core.ui.generated.resources.discussion_type_offline
import dialog.core.ui.generated.resources.discussion_type_online
import org.jetbrains.compose.resources.stringResource

// TODO 도메인 모델 이동 후 매퍼만 남겨야 함
enum class Category {
    ANDROID,
    BACKEND,
    FRONTEND,
    ONLINE,
    OFFLINE,
}

@Composable
fun Category.toChipCategory(): ChipCategory = when (this) {
    Category.ANDROID -> ChipCategory(
        text = stringResource(Res.string.category_android),
        textColor = Color(0xFF003D2E),
        backgroundColor = Color(0xFF3DDC84),
    )

    Category.BACKEND -> ChipCategory(
        text = stringResource(Res.string.category_backend),
        textColor = Color.White,
        backgroundColor = Color(0xFFFF6F00),
    )

    Category.FRONTEND -> ChipCategory(
        text = stringResource(Res.string.category_frontend),
        textColor = Color.White,
        backgroundColor = Color(0xFF2196F3),
    )

    Category.ONLINE -> ChipCategory(
        text = stringResource(Res.string.discussion_type_online),
        textColor = Color.White,
        backgroundColor = Color(0xFF000000),
    )

    Category.OFFLINE -> ChipCategory(
        text = stringResource(Res.string.discussion_type_offline),
        textColor = Color.White,
        backgroundColor = Color(0xFF000000),
    )
}
