package com.on.dialog.data.repository

import com.on.dialog.domain.repository.UserRepository
import com.on.model.common.ProfileImage
import com.on.model.common.Track
import com.on.model.user.UserInfo
import com.on.network.datasource.UserDatasource

internal class UserDefaultRepository(
    private val userDatasource: UserDatasource,
) : UserRepository {
    override suspend fun getMyUserInfo(): Result<UserInfo> =
        userDatasource.getMyUserInfo().mapCatching { it.toDomain() }

    override suspend fun updateMyProfile(
        nickname: String,
        track: Track,
    ): Result<Unit?> = userDatasource.updateMyProfile(nickname, track.name)

    override suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<Boolean> =
        userDatasource
            .updateNotificationSetting(isNotificationEnable)
            .mapCatching { it.isNotificationEnable }

    override suspend fun getMyProfileImage(): Result<ProfileImage> =
        userDatasource.getMyProfileImage().mapCatching { it.toDomain() }

    override suspend fun updateMyProfileImage(request: String): Result<ProfileImage> =
        userDatasource
            .updateMyProfileImage(request)
            .mapCatching { it.toDomain() }

    override suspend fun getMyTrack(): Result<Track> =
        userDatasource.getMyTrack().mapCatching { Track.of(it.track) }
}
