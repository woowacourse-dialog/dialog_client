package com.on.dialog.domain.usecase.pushtoken

import com.on.dialog.domain.repository.DevicePushTokenRepository
import com.on.dialog.domain.repository.FirebaseRepository

class UnregisterPushTokenUseCase(
    private val devicePushTokenRepository: DevicePushTokenRepository,
    private val firebaseRepository: FirebaseRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val savedTokenId: Long = devicePushTokenRepository.getPushTokenId() ?: return@runCatching

        firebaseRepository
            .deleteFcmToken(tokenId = savedTokenId)
            .onSuccess { devicePushTokenRepository.clearPushToken() }
            .getOrThrow()
    }
}
