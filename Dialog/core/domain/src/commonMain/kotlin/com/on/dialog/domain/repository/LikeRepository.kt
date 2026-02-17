package com.on.dialog.domain.repository

import com.on.dialog.model.like.LikeStatus

interface LikeRepository {
    suspend fun postLike(discussionId: Long): Result<Unit>

    suspend fun deleteLike(discussionId: Long): Result<Unit>

    suspend fun getLikeStatus(discussionId: Long): Result<LikeStatus>
}
