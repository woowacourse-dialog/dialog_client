package com.on.dialog.discussiondetail.impl.usecase

import com.on.dialog.domain.repository.LikeRepository

internal class ToggleDiscussionLikeUseCase(
    private val likeRepository: LikeRepository,
) {
    suspend operator fun invoke(
        discussionId: Long,
        isCurrentlyLiked: Boolean,
    ): Result<Unit> =
        if (isCurrentlyLiked) {
            likeRepository.deleteLike(discussionId = discussionId)
        } else {
            likeRepository.postLike(discussionId = discussionId)
        }
}
