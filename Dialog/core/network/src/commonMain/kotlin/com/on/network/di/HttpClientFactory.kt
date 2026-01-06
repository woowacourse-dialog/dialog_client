package com.on.network.di

import com.on.dialog.core.network.BuildKonfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

expect fun createHttpClient(): HttpClient

internal fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = true
            },
        )
    }
}

internal fun HttpClientConfig<*>.installLogging() {
    if (!BuildKonfig.IS_DEBUG) return
    install(Logging) {
        logger = PrettyLogger
        level = LogLevel.ALL
    }
}

@OptIn(ExperimentalSerializationApi::class)
private object PrettyLogger : Logger {
    private val jsonConfiguration = Json {
        prettyPrintIndent = "\t"
        prettyPrint = true
    }

    override fun log(message: String) {
        val replacedMessage: String = replaceBodyWithPrettyJson(message)
        Napier.v(tag = "KtorLogger", message = replacedMessage)
    }

    private fun Json.prettyJson(json: String): String = try {
        val parsed = parseToJsonElement(json)
        encodeToString(JsonElement.serializer(), parsed)
    } catch (e: Exception) {
        Napier.e(tag = "KtorLogger", throwable = e, message = e.message.orEmpty())
        json
    }

    private fun replaceBodyWithPrettyJson(message: String): String {
        val startToken = "BODY START"
        val endToken = "BODY END"

        val startIndex: Int = message.indexOf(startToken)
        val endIndex: Int = message.indexOf(endToken)

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return message
        }

        val bodyStart: Int = startIndex + startToken.length
        val rawBody: String = message.substring(bodyStart, endIndex).trim()

        val prettyBody: String = runCatching {
            jsonConfiguration.prettyJson(rawBody)
        }.getOrElse {
            rawBody
        }

        val before: String = message.take(bodyStart)
        val after: String = message.substring(endIndex)

        return buildString {
            appendLine("----------".repeat(10))
            append(before)
            append("\n")
            append(prettyBody.prependIndent("\t"))
            append("\n")
            appendLine(after)
            appendLine("----------".repeat(10))
        }
    }
}
