package com.on.dialog.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MyTrackGetTrackResponse(
    @SerialName("track")
    val track: String,
)
