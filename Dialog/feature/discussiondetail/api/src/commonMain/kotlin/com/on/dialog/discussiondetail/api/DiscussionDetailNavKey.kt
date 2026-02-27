package com.on.dialog.discussiondetail.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailNavKey(
    val discussionId: Long,
) : NavKey
