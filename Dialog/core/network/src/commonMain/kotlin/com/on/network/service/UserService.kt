package com.on.network.service

import com.on.network.dto.common.DataResponse
import com.on.network.dto.user.MyTrackGetTrackResponse
import com.on.network.dto.user.NotificationSettingRequest
import com.on.network.dto.user.NotificationSettingResponse
import com.on.network.dto.user.ProfileImageGetResponse
import com.on.network.dto.user.ProfileImageUpdateResponse
import com.on.network.dto.user.UserInfoResponse
import com.on.network.dto.user.UserMypageUpdateRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UserService {
    @GET("api/user/mine")
    suspend fun getMyUserInfo(): DataResponse<UserInfoResponse>

    @PATCH("api/user/mine")
    suspend fun updateMyProfile(
        @Body
        request: UserMypageUpdateRequest,
    ): DataResponse<Unit>

    @PATCH("api/user/mine/notifications")
    suspend fun updateNotificationSetting(
        @Body
        request: NotificationSettingRequest,
    ): DataResponse<NotificationSettingResponse>

    @GET("api/user/mine/profile-image")
    suspend fun getMyProfileImage(): DataResponse<ProfileImageGetResponse>

    @PATCH("api/user/mine/profile-image")
    suspend fun updateMyProfileImage(
        @Body
        request: MultiPartFormDataContent, // 업로드할 이미지 파일 Binary (JPEG, PNG 등)
    ): DataResponse<ProfileImageUpdateResponse>

    @GET("api/user/mine/track")
    suspend fun getMyTrack(): DataResponse<MyTrackGetTrackResponse>
}
