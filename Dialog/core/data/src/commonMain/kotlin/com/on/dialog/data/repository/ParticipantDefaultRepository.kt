package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.model.participation.ParticipationStatus
import com.on.dialog.network.datasource.ParticipantDatasource

internal class ParticipantDefaultRepository(
    private val participantDatasource: ParticipantDatasource,
) : ParticipantRepository {
    override suspend fun postParticipation(discussionId: Long): Result<Unit> =
        participantDatasource.postParticipation(id = discussionId)

    override suspend fun getParticipationStatus(discussionId: Long): Result<ParticipationStatus> =
        participantDatasource.getParticipationStatus(id = discussionId).map { it.toDomain() }
}
