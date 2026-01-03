package com.on.dialog.data.di

import io.ktor.client.HttpClient

expect object HttpClientFactory {
    fun create(): HttpClient
}
