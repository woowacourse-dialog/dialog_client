package com.on.dialog.data.repository

import com.on.dialog.domain.repository.FirebaseRepository
import com.on.dialog.model.firebase.Token
import com.on.dialog.model.firebase.TokenId
import com.on.dialog.network.datasource.FirebaseDatasource
import com.on.dialog.network.dto.firebase.TokenRequest

class FirebaseDefaultRepository(
    private val firebaseDatasource: FirebaseDatasource,
) : FirebaseRepository {
    override suspend fun getFcmToken(): Result<Token> =
        firebaseDatasource.getFcmToken().map { Token(value = it.token) }

    override suspend fun postFcmToken(token: String): Result<TokenId> =
        firebaseDatasource
            .postFcmToken(tokenRequest = TokenRequest(token = token))
            .map { TokenId(value = it.tokenId) }

    override suspend fun patchFcmToken(tokenId: Long, token: String): Result<Unit> =
        firebaseDatasource.patchFcmToken(
            tokenId = tokenId,
            tokenRequest = TokenRequest(token = token),
        )

    override suspend fun deleteFcmToken(tokenId: Long): Result<Unit> =
        firebaseDatasource.deleteFcmToken(tokenId)
}
