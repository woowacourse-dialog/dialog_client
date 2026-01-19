package com.on.network.datasource

import com.on.network.dto.session.CookieNetworkEntity

interface CookieStore {
    suspend fun save(cookie: CookieNetworkEntity)

    suspend fun loadAll(requestHost: String, requestPath: String): List<CookieNetworkEntity>

    suspend fun clear()
}
