package com.on.network.di

import com.on.dialog.core.network.BuildKonfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

expect fun createHttpClient(): HttpClient

internal fun HttpClientConfig<*>.installContentNegotiation() {

    /**
     * HTTP 응답이 성공(2xx)이 아닐 경우
     * 자동으로 예외를 발생시키기 위한 설정
     *
     * - ClientRequestException : 4xx (클라이언트 요청 오류)
     * - ServerResponseException : 5xx (서버 오류)
     * - IOException : 네트워크 오류
     *
     * 이 설정이 없으면 400 / 500 에러 응답도
     * 정상 응답처럼 JSON 파싱을 시도하게 되어
     * 의도하지 않은 파싱 오류가 발생할 수 있다.
     */
    expectSuccess = true

    /**
     * 모든 요청의 기본 Content-Type을 JSON으로 설정
     *
     * Retrofit의
     * addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
     * 와 동일한 역할을 하며,
     *
     * 서버에 전송되는 요청 본문이
     * application/json 타입임을 명시한다.
     */
    defaultRequest{
        contentType(ContentType.Application.Json)
    }

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

        val prettyBody: String =
            if (rawBody.isBlank()) {
                rawBody
            } else {
                jsonConfiguration.prettyJson(rawBody)
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
