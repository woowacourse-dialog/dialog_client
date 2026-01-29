package com.on.dialog.data.extension

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
internal actual fun readFileBytes(filePath: String): ByteArray {
    val url = NSURL.fileURLWithPath(filePath.removePrefix("file://"))
    val nsData = NSData.dataWithContentsOfURL(url) ?: error("파일 찾을 수 없음, 경로: $filePath")
    val byteArray = ByteArray(size = nsData.length.toInt()).apply {
        usePinned { pinned ->
            memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }
    }
    return byteArray
}
