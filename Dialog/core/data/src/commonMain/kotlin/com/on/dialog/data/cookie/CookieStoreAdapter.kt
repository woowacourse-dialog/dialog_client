package com.on.dialog.data.cookie

import com.on.dialog.core.local.datasourceimpl.LocalCookieStorage
import com.on.dialog.core.local.dto.session.CookieLocalEntity
import com.on.network.datasource.CookieStore
import com.on.network.dto.session.CookieNetworkEntity

class CookieStoreAdapter(
    private val localCookieStorage: LocalCookieStorage,
) : CookieStore {
    override suspend fun save(cookie: CookieNetworkEntity) {
        localCookieStorage.save(cookie.toLocalEntity())
    }

    override suspend fun loadAll(
        requestHost: String,
        requestPath: String,
    ): List<CookieNetworkEntity> =
        localCookieStorage.loadAll(requestHost, requestPath).map { it.toNetworkEntity() }

    override suspend fun clear() {
        localCookieStorage.clear()
    }

    private fun CookieNetworkEntity.toLocalEntity(): CookieLocalEntity = CookieLocalEntity(
        name = name,
        value = value,
        domain = domain,
        path = path,
        expires = expires,
        secure = secure,
        httpOnly = httpOnly,
    )

    private fun CookieLocalEntity.toNetworkEntity(): CookieNetworkEntity = CookieNetworkEntity(
        name = name,
        value = value,
        domain = domain,
        path = path,
        expires = expires,
        secure = secure,
        httpOnly = httpOnly,
    )
}
