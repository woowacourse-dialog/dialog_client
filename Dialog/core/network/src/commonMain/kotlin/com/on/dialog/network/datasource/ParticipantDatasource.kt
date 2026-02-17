package com.on.dialog.network.datasource

import com.on.dialog.network.dto.participation.ParticipationStatusResponse

interface ParticipantDatasource {
    suspend fun postParticipation(id: Long): Result<Unit>

    suspend fun getParticipationStatus(id: Long): Result<ParticipationStatusResponse>
}
