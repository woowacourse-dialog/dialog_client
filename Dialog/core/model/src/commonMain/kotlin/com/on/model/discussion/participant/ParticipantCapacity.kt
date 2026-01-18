package com.on.model.discussion.participant

data class ParticipantCapacity(
    val current: Int,
    val max: Int,
) {
    fun isParticipantFull(): Boolean =
        current >= max
}
