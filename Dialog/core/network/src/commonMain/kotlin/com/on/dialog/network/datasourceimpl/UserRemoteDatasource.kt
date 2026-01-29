package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.UserDatasource
import com.on.dialog.network.dto.user.MyTrackGetTrackResponse
import com.on.dialog.network.dto.user.NotificationSettingRequest
import com.on.dialog.network.dto.user.NotificationSettingResponse
import com.on.dialog.network.dto.user.ProfileImageGetResponse
import com.on.dialog.network.dto.user.ProfileImageUpdateResponse
import com.on.dialog.network.dto.user.UserInfoResponse
import com.on.dialog.network.dto.user.UserMypageUpdateRequest
import com.on.dialog.network.service.UserService
import io.ktor.client.request.forms.MultiPartFormDataContent


internal class UserRemoteDatasource(
    private val userService: UserService,
) : UserDatasource {
    override suspend fun getMyUserInfo(): Result<UserInfoResponse> =
        safeApiCall { userService.getMyUserInfo() }

    override suspend fun updateMyProfile(request: UserMypageUpdateRequest): Result<Unit> =
        safeApiCall { userService.updateMyProfile(request = request) }

    override suspend fun updateNotificationSetting(request: NotificationSettingRequest): Result<NotificationSettingResponse> =
        safeApiCall { userService.updateNotificationSetting(request = request) }

    override suspend fun getMyProfileImage(): Result<ProfileImageGetResponse> =
        safeApiCall { userService.getMyProfileImage() }

    override suspend fun updateMyProfileImage(request: MultiPartFormDataContent): Result<ProfileImageUpdateResponse> =
        safeApiCall { userService.updateMyProfileImage(request = request) }

    override suspend fun getMyTrack(): Result<MyTrackGetTrackResponse> =
        safeApiCall { userService.getMyTrack() }
}
