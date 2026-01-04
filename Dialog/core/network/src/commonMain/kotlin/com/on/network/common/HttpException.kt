package com.on.network.common

import de.jensklingenberg.ktorfit.Response

/**
 * Ktrofit에는 HtttpException을 가지고 있지 않아
 Retrofit의 HttpException을 참고하여 간단하게 구현
 **/

class HttpException(
    response: Response<*>
) : RuntimeException(getMessage(response)) {
    companion object {
        private fun getMessage(response: Response<*>): String {
            val code: Int = response.status.value
            val message: String = response.status.description

            requireNotNull(response) { "response가 null값입니다." }
            return "HTTP $code $message"
        }
    }
}
