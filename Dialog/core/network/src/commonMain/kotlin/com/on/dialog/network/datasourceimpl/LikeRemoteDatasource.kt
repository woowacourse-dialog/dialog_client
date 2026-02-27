package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.LikeDatasource
import com.on.dialog.network.dto.like.LikeStatusResponse
import com.on.dialog.network.service.LikeService

internal class LikeRemoteDatasource(
    private val likeService: LikeService,
) : LikeDatasource {
    override suspend fun postLike(id: Long): Result<Unit> =
        safeApiCall { likeService.postLike(id = id) }

    override suspend fun deleteLike(id: Long): Result<Unit> =
        safeApiCall { likeService.deleteLike(id = id) }

    override suspend fun getLikeStatus(id: Long): Result<LikeStatusResponse> =
        safeApiCall { likeService.getLike(id = id) }
}
