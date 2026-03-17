package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.FirebaseDatasource
import com.on.dialog.network.dto.firebase.MyTokenResponse
import com.on.dialog.network.dto.firebase.TokenCreationResponse
import com.on.dialog.network.service.FirebaseService

class FirebaseRemoteDatasource(
    private val firebaseService: FirebaseService,
) : FirebaseDatasource {
    override suspend fun getFcmToken(): Result<MyTokenResponse> = safeApiCall { firebaseService.getFcmToken() }

    override suspend fun postFcmToken(): Result<TokenCreationResponse> = safeApiCall { firebaseService.postFcmToken() }

    override suspend fun patchFcmToken(tokenId: Long): Result<Unit> = safeApiCall { firebaseService.patchFcmToken(tokenId = tokenId) }

    override suspend fun deleteFcmToken(tokenId: Long): Result<Unit> = safeApiCall { firebaseService.deleteFcmToken(tokenId = tokenId) }
}
