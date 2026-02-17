package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.ParticipantDatasource
import com.on.dialog.network.dto.participation.ParticipationStatusResponse
import com.on.dialog.network.service.ParticipantService

internal class ParticipantRemoteDatasource(
    private val participantService: ParticipantService,
) : ParticipantDatasource {
    override suspend fun postParticipation(id: Long): Result<Unit> =
        safeApiCall { participantService.postParticipation(id = id) }

    override suspend fun getParticipationStatus(id: Long): Result<ParticipationStatusResponse> =
        safeApiCall { participantService.getParticipationStatus(id = id) }
}
