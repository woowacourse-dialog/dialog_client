package com.on.dialog.data.extension

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

internal fun createImageMultiPartFormDataContent(
    key: String,
    uri: String,
): MultiPartFormDataContent {
    val fileName = uri.substringAfterLast("file://")
    val contentType = when (fileName.substringAfterLast('.').lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "heic", "heif" -> "image/heic"
        else -> "image/png"
    }
    val ext = contentType.removePrefix("image/")
    return MultiPartFormDataContent(
        parts = formData {
            append(
                key = key,
                value = readFileBytes(filePath = uri),
                headers = Headers.build {
                    append(name = HttpHeaders.ContentType, value = contentType)
                    append(
                        name = HttpHeaders.ContentDisposition,
                        value = "filename=profile_image.$ext",
                    )
                },
            )
        },
    )
}
