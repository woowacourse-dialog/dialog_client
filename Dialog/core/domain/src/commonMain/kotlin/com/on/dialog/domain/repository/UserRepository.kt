package com.on.dialog.domain.repository

import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo

interface UserRepository {
    suspend fun getMyUserInfo(): Result<com.on.dialog.model.user.UserInfo>

    suspend fun updateMyProfile(
        nickname: String,
        track: com.on.dialog.model.common.Track,
    ): Result<Unit>

    suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<Boolean>

    suspend fun getMyProfileImage(): Result<com.on.dialog.model.common.ProfileImage>

    suspend fun updateMyProfileImage(request: String): Result<com.on.dialog.model.common.ProfileImage>

    suspend fun getMyTrack(): Result<com.on.dialog.model.common.Track>
}
