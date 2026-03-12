package com.on.dialog.discussiondetail.impl.usecase

import com.on.dialog.domain.repository.ScrapRepository

internal class ToggleDiscussionBookmarkUseCase(
    private val scrapRepository: ScrapRepository,
) {
    suspend operator fun invoke(
        discussionId: Long,
        isCurrentlyBookmarked: Boolean,
    ): Result<Unit> =
        if (isCurrentlyBookmarked) {
            scrapRepository.deleteScrap(discussionId = discussionId)
        } else {
            scrapRepository.postScrap(discussionId = discussionId)
        }
}
