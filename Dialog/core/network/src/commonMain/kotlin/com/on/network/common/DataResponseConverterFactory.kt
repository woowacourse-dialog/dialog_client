package com.on.network.common

import com.on.network.dto.common.DataResponse
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentLength

class DataResponseConverterFactory : Converter.Factory {
    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit,
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        // 현재 Service 단에서 메서드의 반환값의 response타입이 DataResponse인지 확인
        if (typeData.typeInfo.kotlinType?.classifier == DataResponse::class) {
            return object : Converter.SuspendResponseConverter<HttpResponse, Any?> {
                override suspend fun convert(result: KtorfitResult): Any = when (result) {
                    is KtorfitResult.Success -> {
                        val response: HttpResponse = result.response
                        // Response Body가 비어있는 상황이면 DataResponse Unit으로 반환
                        if (response.status == HttpStatusCode.NoContent || response.contentLength() == 0L) {
                            DataResponse(data = Unit)
                        } else {
                            try {
                                // Response Body가 비어있지 않은 상황이면 그대로 반환
                                response.body(typeData.typeInfo)
                            } catch (e: Exception) {
                                // 파싱에 실패하면 그냥 터뜨려버릴지 아니면 일단 DataResponse Unit으로 반환하고 파싱에 실패했다고 말해줄지 고민
                                Napier.w(
                                    tag = "DataResponseConverter",
                                    message = "파싱에 실패했습니다.",
                                    throwable = e,
                                )
                                DataResponse(data = Unit)
                            }
                        }
                    }

                    is KtorfitResult.Failure -> {
                        throw result.throwable
                    }
                }
            }
        }
        return null
    }
}
