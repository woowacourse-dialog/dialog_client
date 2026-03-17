package com.on.dialog.domain.usecase.pushtoken

import com.on.dialog.domain.repository.DevicePushTokenRepository
import com.on.dialog.domain.repository.FirebaseRepository
import com.on.dialog.model.firebase.TokenId

class RegisterPushTokenUseCase(
    private val devicePushTokenRepository: DevicePushTokenRepository,
    private val firebaseRepository: FirebaseRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val deviceToken: String = devicePushTokenRepository.getDeviceToken()
        val savedTokenId: Long? = devicePushTokenRepository.getPushTokenId()

        if (savedTokenId == null) {
            firebaseRepository
                .postFcmToken(token = deviceToken)
                .onSuccess { tokenId: TokenId ->
                    devicePushTokenRepository.savePushTokenId(tokenId.value)
                }.getOrThrow()
        } else {
            firebaseRepository
                .patchFcmToken(tokenId = savedTokenId, token = deviceToken)
                .getOrThrow()
        }
    }
}
