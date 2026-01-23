package com.on.dialog.data.cookie

import com.on.dialog.core.local.datasourceimpl.LocalCookieStorage
import com.on.dialog.core.local.dto.session.CookieLocalEntity
import com.on.dialog.network.datasource.CookieStore
import com.on.dialog.network.dto.session.CookieNetworkEntity

class CookieStoreAdapter(
    private val localCookieStorage: LocalCookieStorage,
) : com.on.dialog.network.datasource.CookieStore {
    override suspend fun save(cookie: com.on.dialog.network.dto.session.CookieNetworkEntity) {
        localCookieStorage.save(cookie = cookie.toLocalEntity())
    }

    override suspend fun loadAll(
        requestHost: String,
        requestPath: String,
    ): List<com.on.dialog.network.dto.session.CookieNetworkEntity> =
        localCookieStorage
            .loadAll(requestHost = requestHost, requestPath = requestPath)
            .map { it.toNetworkEntity() }

    override suspend fun clear() {
        localCookieStorage.clear()
    }

    private fun com.on.dialog.network.dto.session.CookieNetworkEntity.toLocalEntity(): CookieLocalEntity = CookieLocalEntity(
        name = name,
        value = value,
        domain = domain,
        path = path,
        expires = expires,
        secure = secure,
        httpOnly = httpOnly,
    )

    private fun CookieLocalEntity.toNetworkEntity(): com.on.dialog.network.dto.session.CookieNetworkEntity =
        _root_ide_package_.com.on.dialog.network.dto.session.CookieNetworkEntity(
            name = name,
            value = value,
            domain = domain,
            path = path,
            expires = expires,
            secure = secure,
            httpOnly = httpOnly,
        )
}
