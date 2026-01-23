package com.on.dialog.model.discussion.content

import com.on.dialog.model.common.ProfileImage

data class Author(
    val id: Long,
    val nickname: String,
    val profileImage: com.on.dialog.model.common.ProfileImage?,
)
