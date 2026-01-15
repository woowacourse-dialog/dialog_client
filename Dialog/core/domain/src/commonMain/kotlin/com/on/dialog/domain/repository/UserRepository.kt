package com.on.dialog.domain.repository

import com.on.model.common.ProfileImage
import com.on.model.common.Track
import com.on.model.user.UserInfo

interface UserRepository {
    suspend fun getMyUserInfo(): Result<UserInfo>

    suspend fun updateMyProfile(nickname: String, track: Track): Result<Unit?>

    suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<Boolean>

    suspend fun getMyProfileImage(): Result<ProfileImage>

    suspend fun updateMyProfileImage(request: String): Result<ProfileImage>

    suspend fun getMyTrack(): Result<Track>
}
