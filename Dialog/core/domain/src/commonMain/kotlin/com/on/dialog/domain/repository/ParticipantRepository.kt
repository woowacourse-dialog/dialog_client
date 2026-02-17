package com.on.dialog.domain.repository

import com.on.dialog.model.participation.ParticipationStatus

interface ParticipantRepository {
    suspend fun postParticipation(discussionId: Long): Result<Unit>

    suspend fun getParticipationStatus(discussionId: Long): Result<ParticipationStatus>
}
