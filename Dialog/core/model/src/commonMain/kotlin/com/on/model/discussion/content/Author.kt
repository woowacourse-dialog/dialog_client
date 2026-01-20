package com.on.model.discussion.content

import com.on.model.common.ProfileImage

data class Author(
    val id: Long,
    val nickname: String,
    val profileImage: ProfileImage?,
)
