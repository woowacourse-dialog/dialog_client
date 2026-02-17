package com.on.dialog.network.datasource

interface ParticipantDatasource {
    suspend fun postParticipation(id: Long): Result<Unit>

    suspend fun getParticipationStatus(id: Long): Result<Unit>
}
