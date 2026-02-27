package com.on.dialog.network.datasource

import com.on.dialog.network.dto.like.LikeStatusResponse

interface LikeDatasource {
    suspend fun postLike(id: Long): Result<Unit>

    suspend fun deleteLike(id: Long): Result<Unit>

    suspend fun getLikeStatus(id: Long): Result<LikeStatusResponse>
}
