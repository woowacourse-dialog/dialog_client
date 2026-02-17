package com.on.dialog.domain.repository

interface ParticipantRepository {
    suspend fun postParticipation(discussionId: Long): Result<Unit>

    suspend fun getParticipationStatus(discussionId: Long): Result<Unit>
}
