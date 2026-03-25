package com.on.dialog.network.dto.auth

import com.on.dialog.model.auth.OAuthLoginResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OAuthLoginResponse(
    @SerialName("isRegistered")
    val isRegistered: Boolean,
    @SerialName("userId")
    val userId: Long?,
    @SerialName("nickname")
    val nickname: String?,
) {
    fun toDomain(): OAuthLoginResult = OAuthLoginResult(
        isRegistered = isRegistered,
        userId = userId,
        nickname = nickname,
    )
}
