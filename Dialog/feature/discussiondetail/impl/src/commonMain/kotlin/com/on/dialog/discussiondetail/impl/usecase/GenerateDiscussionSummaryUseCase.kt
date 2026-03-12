package com.on.dialog.discussiondetail.impl.usecase

import com.on.dialog.domain.repository.DiscussionRepository
import kotlinx.coroutines.delay

internal class GenerateDiscussionSummaryUseCase(
    private val discussionRepository: DiscussionRepository,
) {
    suspend operator fun invoke(discussionId: Long): Result<String> =
        discussionRepository
            .createDiscussionSummary(discussionId = discussionId)
            .map { pollSummaryUntilLoaded(discussionId = discussionId) }

    private suspend fun fetchDiscussionSummary(discussionId: Long): Result<String?> =
        discussionRepository.getDiscussionDetail(id = discussionId).map { detail -> detail.summary }

    private suspend fun pollSummaryUntilLoaded(discussionId: Long): String {
        repeat(SUMMARY_POLL_MAX_RETRY) {
            delay(SUMMARY_POLL_INTERVAL_MS)
            val summary = fetchDiscussionSummary(discussionId = discussionId).getOrThrow()
            if (summary != null) return summary
        }
        throw IllegalStateException(CANT_LOAD_SUMMARY_ERROR_MESSAGE)
    }

    companion object {
        private const val SUMMARY_POLL_INTERVAL_MS = 10_000L
        private const val SUMMARY_POLL_MAX_RETRY = 3
        private const val CANT_LOAD_SUMMARY_ERROR_MESSAGE = "생성된 요약을 불러오지 못했습니다."
    }
}
