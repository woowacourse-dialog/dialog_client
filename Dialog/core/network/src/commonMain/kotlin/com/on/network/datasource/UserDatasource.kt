package com.on.network.datasource

import com.on.network.dto.user.MyTrackGetTrackResponse
import com.on.network.dto.user.NotificationSettingRequest
import com.on.network.dto.user.NotificationSettingResponse
import com.on.network.dto.user.ProfileImageGetResponse
import com.on.network.dto.user.ProfileImageUpdateResponse
import com.on.network.dto.user.UserInfoResponse
import com.on.network.dto.user.UserMypageUpdateRequest
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UserDatasource {
    suspend fun getMyUserInfo(): Result<UserInfoResponse>

    suspend fun updateMyProfile(request: UserMypageUpdateRequest): Result<Unit>

    suspend fun updateNotificationSetting(request: NotificationSettingRequest): Result<NotificationSettingResponse>

    suspend fun getMyProfileImage(): Result<ProfileImageGetResponse>

    suspend fun updateMyProfileImage(request: MultiPartFormDataContent): Result<ProfileImageUpdateResponse>

    suspend fun getMyTrack(): Result<MyTrackGetTrackResponse>
}
