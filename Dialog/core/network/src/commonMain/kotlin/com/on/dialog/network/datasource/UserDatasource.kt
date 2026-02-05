package com.on.dialog.network.datasource

import com.on.dialog.network.dto.user.MyTrackGetTrackResponse
import com.on.dialog.network.dto.user.NotificationSettingRequest
import com.on.dialog.network.dto.user.NotificationSettingResponse
import com.on.dialog.network.dto.user.ProfileImageGetResponse
import com.on.dialog.network.dto.user.ProfileImageUpdateResponse
import com.on.dialog.network.dto.user.UserInfoResponse
import com.on.dialog.network.dto.user.UserMypageUpdateRequest
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UserDatasource {
    suspend fun getMyUserInfo(): Result<UserInfoResponse>

    suspend fun updateMyProfile(request: UserMypageUpdateRequest): Result<Unit>

    suspend fun deleteMyAccount(): Result<Unit>

    suspend fun updateNotificationSetting(request: NotificationSettingRequest): Result<NotificationSettingResponse>

    suspend fun getMyProfileImage(): Result<ProfileImageGetResponse>

    suspend fun updateMyProfileImage(request: MultiPartFormDataContent): Result<ProfileImageUpdateResponse>

    suspend fun getMyTrack(): Result<MyTrackGetTrackResponse>
}
