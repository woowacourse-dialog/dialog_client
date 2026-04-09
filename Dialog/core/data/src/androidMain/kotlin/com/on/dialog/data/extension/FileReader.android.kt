package com.on.dialog.data.extension

import java.io.File

internal actual fun readFileBytes(filePath: String): ByteArray = File(filePath).readBytes()
