package com.on.dialog.data.repository

import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.model.like.LikeStatus
import com.on.dialog.network.datasource.LikeDatasource

internal class LikeDefaultRepository(
    private val likeDatasource: LikeDatasource,
) : LikeRepository {
    override suspend fun postLike(discussionId: Long): Result<Unit> =
        likeDatasource.postLike(id = discussionId)

    override suspend fun deleteLike(discussionId: Long): Result<Unit> =
        likeDatasource.deleteLike(id = discussionId)

    override suspend fun getLikeStatus(discussionId: Long): Result<LikeStatus> =
        likeDatasource
            .getLikeStatus(id = discussionId)
            .map { it.toDomain() }
}
