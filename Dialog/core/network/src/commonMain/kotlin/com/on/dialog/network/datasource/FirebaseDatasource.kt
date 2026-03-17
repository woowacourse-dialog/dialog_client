package com.on.dialog.network.datasource

import com.on.dialog.network.dto.firebase.MyTokenResponse
import com.on.dialog.network.dto.firebase.TokenCreationResponse

interface FirebaseDatasource {
    suspend fun getFcmToken(): Result<MyTokenResponse>

    suspend fun postFcmToken(): Result<TokenCreationResponse>

    suspend fun patchFcmToken(tokenId: Long): Result<Unit>

    suspend fun deleteFcmToken(tokenId: Long): Result<Unit>
}
