package com.on.dialog.domain.repository

import com.on.dialog.model.firebase.Token
import com.on.dialog.model.firebase.TokenId

interface FirebaseRepository {
    suspend fun getFcmToken(): Result<Token>

    suspend fun postFcmToken(token: String): Result<TokenId>

    suspend fun patchFcmToken(tokenId: Long, token: String): Result<Unit>

    suspend fun deleteFcmToken(tokenId: Long): Result<Unit>
}
