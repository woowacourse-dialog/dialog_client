package com.on.dialog.feature.signup.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.model.common.Track

@Immutable
data class SignUpState(
    val selectedTrack: Track? = null,
    val isNotificationEnabled: Boolean = false,
) {
    val isTrackUnSelected: Boolean = selectedTrack == null
    val selectedTrackIndex: Int? = selectedTrack?.let { track -> Track.entries.indexOf(track) }
}
