package com.on.dialog.network.dto.participation

import com.on.dialog.model.participation.ParticipationStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ParticipationStatusResponse(
    @SerialName("isParticipation") val isParticipation: Boolean,
) {
    fun toDomain(): ParticipationStatus = ParticipationStatus(isParticipation = isParticipation)
}
