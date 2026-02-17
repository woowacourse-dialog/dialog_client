package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.datasource.ParticipantDatasource
import com.on.dialog.network.service.ParticipantService

internal class ParticipantRemoteDatasource(
    private val participantService: ParticipantService,
) : ParticipantDatasource {
    override suspend fun postParticipation(id: Long): Result<Unit> =
        runCatching { participantService.postParticipation(id = id) }

    override suspend fun getParticipationStatus(id: Long): Result<Unit> =
        runCatching { participantService.getParticipationStatus(id = id) }
}
