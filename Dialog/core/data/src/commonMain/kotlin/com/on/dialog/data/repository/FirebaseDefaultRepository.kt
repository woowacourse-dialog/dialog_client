package com.on.dialog.data.repository

import com.on.dialog.domain.repository.FirebaseRepository
import com.on.dialog.model.firebase.Token
import com.on.dialog.model.firebase.TokenId
import com.on.dialog.network.datasource.FirebaseDatasource

class FirebaseDefaultRepository(
    private val firebaseDatasource: FirebaseDatasource
) : FirebaseRepository {
    override suspend fun getFcmToken(): Result<Token> {
        return firebaseDatasource.getFcmToken().map { Token(value = it.token) }
    }

    override suspend fun postFcmToken(): Result<TokenId> {
        return firebaseDatasource.postFcmToken().map { TokenId(value = it.tokenId) }
    }

    override suspend fun patchFcmToken(tokenId: Long): Result<Unit> {
        return firebaseDatasource.patchFcmToken(tokenId)
    }

    override suspend fun deleteFcmToken(tokenId: Long): Result<Unit> {
        return firebaseDatasource.deleteFcmToken(tokenId)
    }
}