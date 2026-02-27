package com.on.dialog.network.dto.like

import com.on.dialog.model.like.LikeStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LikeStatusResponse(
    @SerialName("isLiked")
    val isLiked: Boolean,
) {
    fun toDomain(): LikeStatus = LikeStatus(isLiked = isLiked)
}
