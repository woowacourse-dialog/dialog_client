package com.on.dialog.data.extension

/**
 * 지정된 경로의 파일을 바이트 배열로 읽어 반환합니다.
 *
 * @param filePath 읽을 파일의 경로
 * @return 파일의 내용을 담은 [ByteArray]
 * @throws IllegalStateException 파일을 찾을 수 없거나 읽을 수 없는 경우 (iOS)
 */
internal expect fun readFileBytes(filePath: String): ByteArray
