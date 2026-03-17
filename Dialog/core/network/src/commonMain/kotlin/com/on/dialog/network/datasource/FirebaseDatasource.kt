package com.on.dialog.network.datasource

import com.on.dialog.network.dto.firebase.MyTokenResponse
import com.on.dialog.network.dto.firebase.TokenCreationResponse
import com.on.dialog.network.dto.firebase.TokenRequest

interface FirebaseDatasource {
    suspend fun getFcmToken(): Result<MyTokenResponse>

    suspend fun postFcmToken(tokenRequest: TokenRequest): Result<TokenCreationResponse>

    suspend fun patchFcmToken(tokenId: Long, tokenRequest: TokenRequest): Result<Unit>

    suspend fun deleteFcmToken(tokenId: Long): Result<Unit>
}
