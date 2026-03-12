package com.on.dialog.domain.usecase.discussion.interaction

import com.on.dialog.domain.repository.LikeRepository

class ToggleDiscussionLikeUseCase(
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
