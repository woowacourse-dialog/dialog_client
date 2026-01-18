package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.UserDatasource
import com.on.network.dto.user.MyTrackGetTrackResponse
import com.on.network.dto.user.NotificationSettingRequest
import com.on.network.dto.user.NotificationSettingResponse
import com.on.network.dto.user.ProfileImageGetResponse
import com.on.network.dto.user.ProfileImageUpdateResponse
import com.on.network.dto.user.UserInfoResponse
import com.on.network.dto.user.UserMypageUpdateRequest
import com.on.network.service.UserService

internal class UserRemoteDatasource(
    private val userService: UserService,
) : UserDatasource {
    override suspend fun getMyUserInfo(): Result<UserInfoResponse> =
        safeApiCall { userService.getMyUserInfo() }

    override suspend fun updateMyProfile(nickname: String, track: String): Result<Unit?> =
        safeApiCall { userService.updateMyProfile(UserMypageUpdateRequest(nickname, track)) }

    override suspend fun updateNotificationSetting(isNotificationEnable: Boolean): Result<NotificationSettingResponse> =
        safeApiCall {
            userService.updateNotificationSetting(NotificationSettingRequest(isNotificationEnable))
        }

    override suspend fun getMyProfileImage(): Result<ProfileImageGetResponse> =
        safeApiCall { userService.getMyProfileImage() }

    override suspend fun updateMyProfileImage(file: String): Result<ProfileImageUpdateResponse> =
        safeApiCall { userService.updateMyProfileImage(file) }

    override suspend fun getMyTrack(): Result<MyTrackGetTrackResponse> =
        safeApiCall { userService.getMyTrack() }
}
