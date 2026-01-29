package com.on.dialog.data.repository

import com.on.dialog.data.extension.createImageMultiPartFormDataContent
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo
import com.on.dialog.network.datasource.UserDatasource
import com.on.dialog.network.dto.user.NotificationSettingRequest.Companion.toRequest
import com.on.dialog.network.dto.user.UserMypageUpdateRequest
import io.ktor.client.request.forms.MultiPartFormDataContent

internal class UserDefaultRepository(
    private val userDatasource: UserDatasource,
) : UserRepository {
    override suspend fun getMyUserInfo(): Result<UserInfo> =
        userDatasource.getMyUserInfo().mapCatching { it.toDomain() }

    override suspend fun updateMyProfile(
        nickname: String,
        track: Track,
    ): Result<Unit> =
        userDatasource.updateMyProfile(
            request = UserMypageUpdateRequest(
                nickname = nickname,
                track = track.name,
            ),
        )

    override suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<Boolean> =
        userDatasource
            .updateNotificationSetting(request = isNotificationEnable.toRequest())
            .map { it.isNotificationEnable }

    override suspend fun getMyProfileImage(): Result<ProfileImage> =
        userDatasource.getMyProfileImage().mapCatching { it.toDomain() }

    override suspend fun updateMyProfileImage(uri: String): Result<ProfileImage> {
        val request: MultiPartFormDataContent =
            createImageMultiPartFormDataContent(key = "file", uri = uri)
        return userDatasource.updateMyProfileImage(request = request).mapCatching { it.toDomain() }
    }

    override suspend fun getMyTrack(): Result<Track> =
        userDatasource.getMyTrack().mapCatching { Track.of(name = it.track) }
}
