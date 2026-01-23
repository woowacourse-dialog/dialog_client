package com.on.dialog.data.repository

import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo
import com.on.dialog.network.datasource.UserDatasource
import com.on.dialog.network.dto.user.NotificationSettingRequest.Companion.toRequest
import com.on.dialog.network.dto.user.UserMypageUpdateRequest

internal class UserDefaultRepository(
    private val userDatasource: com.on.dialog.network.datasource.UserDatasource,
) : UserRepository {
    override suspend fun getMyUserInfo(): Result<com.on.dialog.model.user.UserInfo> =
        userDatasource.getMyUserInfo().mapCatching { it.toDomain() }

    override suspend fun updateMyProfile(
        nickname: String,
        track: com.on.dialog.model.common.Track,
    ): Result<Unit> =
        userDatasource.updateMyProfile(
            request = _root_ide_package_.com.on.dialog.network.dto.user.UserMypageUpdateRequest(
                nickname = nickname,
                track = track.name,
            ),
        )

    override suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<Boolean> =
        userDatasource
            .updateNotificationSetting(request = isNotificationEnable.toRequest())
            .map { it.isNotificationEnable }

    override suspend fun getMyProfileImage(): Result<com.on.dialog.model.common.ProfileImage> =
        userDatasource.getMyProfileImage().mapCatching { it.toDomain() }

    override suspend fun updateMyProfileImage(request: String): Result<com.on.dialog.model.common.ProfileImage> =
        userDatasource
            .updateMyProfileImage(file = request)
            .mapCatching { it.toDomain() }

    override suspend fun getMyTrack(): Result<com.on.dialog.model.common.Track> =
        userDatasource
            .getMyTrack()
            .mapCatching {
                _root_ide_package_.com.on.dialog.model.common.Track
                    .of(name = it.track)
            }
}
