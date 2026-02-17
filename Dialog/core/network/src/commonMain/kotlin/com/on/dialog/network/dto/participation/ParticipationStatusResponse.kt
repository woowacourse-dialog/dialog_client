package com.on.dialog.network.dto.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ParticipationStatusResponse(
    @SerialName("isParticipation")
    val isParticipation: Boolean,
)
