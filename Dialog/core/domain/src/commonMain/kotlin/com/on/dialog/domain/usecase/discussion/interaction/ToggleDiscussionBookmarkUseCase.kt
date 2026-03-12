package com.on.dialog.domain.usecase.discussion.interaction

import com.on.dialog.domain.repository.ScrapRepository

class ToggleDiscussionBookmarkUseCase(
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
