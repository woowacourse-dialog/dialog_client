package com.on.network.di

import io.ktor.client.HttpClient

expect object HttpClientFactory {
    fun create(): HttpClient
}
